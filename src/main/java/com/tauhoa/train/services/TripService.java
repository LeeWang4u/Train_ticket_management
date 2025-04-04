package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.TrainInfoDTO;
import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
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
    @Transactional(readOnly = true)
    public List<TripResponseDTO> findTripsByStationsAndDate(TripSearchRequestDTO request) {
        String departureStationName = request.getDepartureStation();
        String arrivalStationName = request.getArrivalStation();
        LocalDate searchDate = request.getTripDate();

        // Lấy station_id từ station_name
        Station departureStationEntity = stationRepository.findByStationName(departureStationName)
                .orElseThrow(() -> new IllegalArgumentException("Departure station not found: " + departureStationName));
        Station arrivalStationEntity = stationRepository.findByStationName(arrivalStationName)
                .orElseThrow(() -> new IllegalArgumentException("Arrival station not found: " + arrivalStationName));

        int departureStationId = departureStationEntity.getStationId();
        int arrivalStationId = arrivalStationEntity.getStationId();

        // Lấy tất cả các chuyến tàu
        List<Trip> allTrips = tripRepository.findAll();

        // Lọc các chuyến tàu phù hợp
        return allTrips.stream()
                .map(trip -> {
                    int trainId = trip.getTrain().getTrainId();
                    List<TrainSchedule> schedules = trainScheduleRepository.findByTrainId(trainId);

                    // Kiểm tra nếu schedules rỗng, bỏ qua chuyến tàu này
                    if (schedules.isEmpty()) {
                        return null; // Không có lịch trình cho tàu này
                    }

                    // Tìm lịch trình cho ga đi và ga đến
                    TrainSchedule departureSchedule = schedules.stream()
                            .filter(ts -> ts.getStation().getStationId() == departureStationId)
                            .findFirst()
                            .orElse(null);
                    TrainSchedule arrivalSchedule = schedules.stream()
                            .filter(ts -> ts.getStation().getStationId() == arrivalStationId)
                            .findFirst()
                            .orElse(null);

                    // Nếu không tìm thấy ga đi hoặc ga đến, hoặc ga đi không trước ga đến, bỏ qua
                    if (departureSchedule == null || arrivalSchedule == null ||
                            departureSchedule.getOrdinalNumber() >= arrivalSchedule.getOrdinalNumber()) {
                        return null;
                    }

                    // Lấy thời gian xuất phát tại ga đầu tiên từ trip_date
                    LocalDateTime tripStart = trip.getTripDate();

                    // Tính thời gian thực tế tại ga đi
                    int depDayOffset = departureSchedule.getDay() - 1; // Số ngày kể từ ngày đầu tiên
                    LocalTime depTime = departureSchedule.getDepartureTime();
                    LocalDateTime departureDateTime = tripStart.plusDays(depDayOffset).with(depTime);

                    // Tính thời gian thực tế tại ga đến
                    int arrDayOffset = arrivalSchedule.getDay() - 1; // Số ngày kể từ ngày đầu tiên
                    LocalTime arrTime = arrivalSchedule.getArrivalTime();
                    LocalDateTime arrivalDateTime = tripStart.plusDays(arrDayOffset).with(arrTime);

                    // Kiểm tra xem ngày tại ga đi có khớp với ngày tìm kiếm không
                    if (!departureDateTime.toLocalDate().equals(searchDate)) {
                        return null;
                    }

                    // Tạo DTO phản hồi
                    return new TripResponseDTO(
                            trip.getTripId(),
                            trip.getTrain().getTrainName(),
                            trip.getBasePrice(),
                            trip.getTripDate(),
                            trip.getTripStatus(),
                            departureStationName,
                            arrivalStationName,
                            departureDateTime,
                            arrivalDateTime
                    );
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    private long calculateHoursBetween(LocalTime startTime, LocalTime endTime) {
        long hours = java.time.Duration.between(startTime, endTime).toHours();
        if (hours < 0) {
            hours += 24; // Xử lý trường hợp qua nửa đêm
        }
        return hours;
    }

    private long calculateMinutesBetween(LocalTime startTime, LocalTime endTime, int startOrdinal, int endOrdinal) {
        long minutes = Duration.between(startTime, endTime).toMinutes();
        // Nếu endTime nhỏ hơn startTime (qua nửa đêm), cộng thêm 24h
        if (minutes < 0) {
            minutes += 24 * 60; // 24 giờ tính bằng phút
        }
        // Nếu ordinal_number tăng qua ngày (ga sau xa hơn ga đầu), điều chỉnh thêm ngày
        int ordinalDiff = endOrdinal - startOrdinal;
        if (ordinalDiff > 0) {
            minutes += (ordinalDiff - 1) * 24 * 60; // Thêm số ngày giữa các ga (trừ 1 vì ngày đầu đã tính)
        }
        return minutes;
    }

}
