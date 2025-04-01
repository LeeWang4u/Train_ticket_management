package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.TrainInfoDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService implements ITripService {
    private final TripRepository tripRepository;
    private final StationRepository stationRepository;
    private final TrainScheduleRepository trainScheduleRepository;
    private final SeatRepository seatRepository;
    private final TicketReservationRepository ticketReservationRepository;
    private final CarriageListRepository carriageListRepository;

    @Override
    public Optional<Trip> getTrip(int id) {
        return tripRepository.findById(id);
    }

    @Override
    public List<Trip> findByTripDate(LocalDate tripDate) {
        return tripRepository.findByTripDate(tripDate);
    }

    @Override
    public List<TrainInfoDTO> getTrainInfoByStationAndDate(String stationName, LocalDate date) {
        List<TrainInfoDTO> result = new ArrayList<>();

        // 1. Tìm station theo tên
        Station station = stationRepository.findByStationName(stationName)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        // 2. Tìm lịch trình tàu đi qua ga này
        List<TrainSchedule> schedules = trainScheduleRepository.findByStationId(station.getStationId());

        // 3. Lọc các chuyến tàu theo ngày
        for (TrainSchedule schedule : schedules) {
            Train train = schedule.getTrain();
            List<Trip> trips = tripRepository.findByTrainIdAndTripDate(train.getTrainId(), date);

            for (Trip trip : trips) {
                TrainInfoDTO trainInfo = new TrainInfoDTO();
                trainInfo.setTrainCode(train.getTrainName());
                trainInfo.setDepartureTime(schedule.getDepartureTime());
                trainInfo.setArrivalTime(schedule.getArrivalTime());

                // 4. Tính số chỗ đặt và chỗ trống
                List<CarriageList> carriages = carriageListRepository.findByTripTripId(trip.getTripId());
                int totalSeats = 0;
                int bookedSeats = 0;
                Map<String, Integer> compartmentSeats = new HashMap<>();

                for (CarriageList carriage : carriages) {
                    Compartment compartment = carriage.getCompartment();
                    List<Seat> seats = seatRepository.findByCarriageListCarriageListId(carriage.getCarriageListId());
                    totalSeats += seats.size();

                    int bookedSeatsInCompartment = 0;
                    for (Seat seat : seats) {
                        List<TicketReservation> reservations = ticketReservationRepository.findBySeatSeatId(seat.getSeatId());
                        if (!reservations.isEmpty()) {
                            bookedSeats++;
                            bookedSeatsInCompartment++;
                        }
                    }

                    // Số ghế trống theo khoang
                    int availableSeatsInCompartment = seats.size() - bookedSeatsInCompartment;
                    compartmentSeats.put(compartment.getCompartmentName(), availableSeatsInCompartment);
                }

                trainInfo.setBookedSeats(bookedSeats);
                trainInfo.setAvailableSeats(totalSeats - bookedSeats);
                trainInfo.setCompartmentAvailableSeats(compartmentSeats);

                result.add(trainInfo);
            }
        }

        return result;
    }

    @Override
    public List<TripResponseDTO> findTrips(String departureStation, String arrivalStation, LocalDate tripDate) {
        // Lấy các chuyến tàu tiềm năng
        List<Trip> trips = tripRepository.findTripsByStations(departureStation, arrivalStation);

        // Lọc và chuyển đổi sang DTO
        return trips.stream()
                .map(trip -> {
                    // Tìm TrainSchedule của ga đi
                    TrainSchedule departureSchedule = trip.getTrain().getTrainSchedules().stream()
                            .filter(ts -> ts.getStation().getStationName().equals(departureStation))
                            .findFirst()
                            .orElse(null);
                    if (departureSchedule == null) return null;

                    // Tính thời gian thực tế tại ga đi
                    LocalDateTime tripStart = trip.getTripDate();
                    LocalTime departureTime = departureSchedule.getDepartureTime();
                    if (departureTime == null) return null;

                    LocalDateTime actualDateTimeAtDeparture = tripStart
                            .plusHours(departureTime.getHour())
                            .plusMinutes(departureTime.getMinute());

                    // Kiểm tra ngày thực tế tại ga đi
                    if (!actualDateTimeAtDeparture.toLocalDate().equals(tripDate)) return null;

                    // Tìm TrainSchedule của ga đến (để lấy tên ga)
                    TrainSchedule arrivalSchedule = trip.getTrain().getTrainSchedules().stream()
                            .filter(ts -> ts.getStation().getStationName().equals(arrivalStation))
                            .findFirst()
                            .orElse(null);
                    if (arrivalSchedule == null) return null;

                    // Tạo DTO
                    return new TripResponseDTO(
                            trip.getTripId(),
                            trip.getTrain().getTrainName(),
                            trip.getBasePrice(),
                            trip.getTripDate(),
                            trip.getTripStatus(),
                            departureStation,
                            arrivalStation,
                            actualDateTimeAtDeparture
                    );
                })
                .filter(dto -> dto != null) // Loại bỏ các DTO null
                .collect(Collectors.toList());
    }


}
