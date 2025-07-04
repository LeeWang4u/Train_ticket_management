package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.AddTripRequestDTO;
import com.tauhoa.train.dtos.request.TrainInfoDTO;
import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final CarriageListRepository carriageListRepository;
    private final TrainRepository trainRepository;
    private final CompartmentRepository compartmentRepository;
    private final TicketRepository ticketRepository;
    private final SeatService seatService;
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
                        List<Ticket> reservations = ticketRepository.findBySeatSeatId(seat.getSeatId());
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

        // Lấy tất cả các chuyến tàu findAllWithStatus
//        List<Trip> allTrips = tripRepository.findAll();
        List<Trip> allTrips = tripRepository.findAllWithStatus();

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
                    LocalDate tripStart = trip.getTripDate();

                    int depDayOffset = departureSchedule.getDay() - 1;
                    LocalDate depDate = tripStart.plusDays(depDayOffset);
                    LocalTime depTime = departureSchedule.getDepartureTime();
                    LocalDateTime departureDateTime = depDate.atTime(depTime);

                    // Tính thời gian tại ga đến
                    int arrDayOffset = arrivalSchedule.getDay() - 1;
                    LocalDate arrDate = tripStart.plusDays(arrDayOffset);
                    LocalTime arrTime = arrivalSchedule.getArrivalTime();
                    LocalDateTime arrivalDateTime = arrDate.atTime(arrTime);
                    // Kiểm tra xem ngày tại ga đi có khớp với ngày tìm kiếm không
                    if (!departureDateTime.toLocalDate().equals(searchDate)) {
                        return null;
                    }

//                    int availableSeats = getNumberEmptySeats(trip.getTripId(), departureStationName, arrivalStationName);
                    int availableSeats = seatService.getNumberEmptySeats(trip.getTripId(), departureStationName, arrivalStationName);
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
                            arrivalDateTime,
                            availableSeats
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

    public int getNumberEmptySeats(int tripId, String departureStationName, String arrivalStationName ){

        // Lấy thông tin chuyến tàu
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found for tripId: " + tripId));

        // Lấy station_id từ station_name
        Station departureStation = stationRepository.findByStationName(departureStationName)
                .orElseThrow(() -> new IllegalArgumentException("Departure station not found: " + departureStationName));
        Station arrivalStation = stationRepository.findByStationName(arrivalStationName)
                .orElseThrow(() -> new IllegalArgumentException("Arrival station not found: " + arrivalStationName));

        int departureStationId = departureStation.getStationId();
        int arrivalStationId = arrivalStation.getStationId();

        // Lấy train_id từ trip
        int trainId = trip.getTrain().getTrainId();

        // Lấy ordinal_number của ga đi và ga đến
        TrainSchedule departureSchedule = trainScheduleRepository.findByTrainIdAndStationId(trainId, departureStationId)
                .orElseThrow(() -> new IllegalArgumentException("Departure station not found in train schedule for trainId: " + trainId));
        TrainSchedule arrivalSchedule = trainScheduleRepository.findByTrainIdAndStationId(trainId, arrivalStationId)
                .orElseThrow(() -> new IllegalArgumentException("Arrival station not found in train schedule for trainId: " + trainId));

        int departureOrdinal = departureSchedule.getOrdinalNumber();
        int arrivalOrdinal = arrivalSchedule.getOrdinalNumber();

        // Kiểm tra thứ tự ga
        if (departureOrdinal >= arrivalOrdinal) {
            throw new IllegalArgumentException("Departure station must be before arrival station");
        }

        // Lấy danh sách toa của chuyến tàu
        List<CarriageList> carriages = carriageListRepository.findByTripId(tripId);
        if (carriages.isEmpty()) {
            throw new IllegalArgumentException("No carriages found for tripId: " + tripId);
        }

        // Tính tổng số ghế từ tất cả các toa (seat_count trong compartment)
        int totalSeats = carriages.stream()
                .mapToInt(carriage -> carriage.getCompartment().getSeatCount())
                .sum();

        // Lấy danh sách vé đã đặt của chuyến tàu
        List<Ticket> reservations = ticketRepository.findByTripIdAndStatusBookedOrHold(tripId);

        // Đếm số ghế đã bị đặt (BOOKED)
        int bookedSeats = (int) reservations.stream()
                .filter(reservation -> {
                    // Lấy ordinal_number của ga đi và ga đến trong vé
                    TrainSchedule resDepartureSchedule = trainScheduleRepository
                            .findByTrainIdAndStationId(trainId, reservation.getDepartureStation().getStationId())
                            .orElseThrow(() -> new IllegalStateException("Invalid reservation data: departure station not found"));
                    TrainSchedule resArrivalSchedule = trainScheduleRepository
                            .findByTrainIdAndStationId(trainId, reservation.getArrivalStation().getStationId())
                            .orElseThrow(() -> new IllegalStateException("Invalid reservation data: arrival station not found"));

                    int resDepartureOrdinal = resDepartureSchedule.getOrdinalNumber();
                    int resArrivalOrdinal = resArrivalSchedule.getOrdinalNumber();

                    // Ghế bị BOOKED nếu hành trình vé chồng lấn với hành trình yêu cầu
                    return resDepartureOrdinal < arrivalOrdinal && resArrivalOrdinal > departureOrdinal;
                })
                .map(Ticket::getSeat) // Lấy ghế từ vé
                .distinct() // Loại bỏ ghế trùng (một ghế có thể bị đặt nhiều lần trong cùng trip)
                .count();

        // Số ghế trống = Tổng số ghế - Số ghế đã bị đặt
        int availableSeats = totalSeats - bookedSeats;

        return availableSeats;
    }

    public Trip findByTripId(int tripId) {
        return tripRepository.findByTripId(tripId);
    }

    @Override
    @Transactional
    public Trip addTrip(AddTripRequestDTO request) {
        // Kiểm tra đầu vào
        if (request.getNumSoftSeatCarriages() < 0 || request.getNumSixBerthCarriages() < 0 || request.getNumFourBerthCarriages() < 0) {
            throw new IllegalArgumentException("Number of carriages cannot be negative");
        }

        // Kiểm tra trainId
        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new IllegalArgumentException("Train not found for trainId: " + request.getTrainId()));

//        // Kiểm tra xem tàu đã có chuyến trong ngày này chưa
//        LocalDate tripDate = request.getTripDate();
//        List<Trip> existingTrips = tripRepository.findByTrainAndTripDate(train, tripDate);
//        if (!existingTrips.isEmpty()) {
//            throw new IllegalArgumentException("Tàu " + train.getTrainName() +" này đã được tạo trong ngày: " + tripDate);
//        }

        // Kiểm tra xem tàu đã có chuyến trong ngày này chưa
//        LocalDate tripDate = request.getTripDate();
//        List<Trip> existingTrips = tripRepository.findByTrainAndTripDate(train, tripDate);
//        if (!existingTrips.isEmpty()) {
//            // Trả về chuyến tàu đã tồn tại
//            return existingTrips.get(0); // Lấy chuyến đầu tiên (vì chỉ có 1 chuyến trong ngày)
//        }

        LocalDate tripDateTime = request.getTripDate();

        // Tạo Trip
        Trip trip = new Trip();
        trip.setTrain(train);
        trip.setBasePrice(request.getBasePrice());
        trip.setTripDate(tripDateTime);
        trip.setTripStatus("Scheduled");
        trip = tripRepository.save(trip);

        // Lấy compartment cho từng loại toa
        Compartment softSeatCompartment = compartmentRepository.findById(1) // Giả định compartment_id=1 là toa ngồi mềm
                .orElseThrow(() -> new IllegalArgumentException("Soft seat compartment not found"));
        Compartment sixBerthCompartment = compartmentRepository.findById(2) // Giả định compartment_id=2 là toa nằm 6
                .orElseThrow(() -> new IllegalArgumentException("Six-berth compartment not found"));
        Compartment fourBerthCompartment = compartmentRepository.findById(3) // Giả định compartment_id=3 là toa nằm 4
                .orElseThrow(() -> new IllegalArgumentException("Four-berth compartment not found"));

        // Tạo danh sách toa (CarriageList)
        List<CarriageList> carriages = new ArrayList<>();
        int stt = 1;

        // Toa ngồi mềm
        for (int i = 0; i < request.getNumSoftSeatCarriages(); i++) {
            CarriageList carriage = new CarriageList();
            carriage.setTrip(trip);
            carriage.setCompartment(softSeatCompartment);
            carriage.setStt(stt++);
            carriages.add(carriage);
        }

        // Toa nằm 6
        for (int i = 0; i < request.getNumSixBerthCarriages(); i++) {
            CarriageList carriage = new CarriageList();
            carriage.setTrip(trip);
            carriage.setCompartment(sixBerthCompartment);
            carriage.setStt(stt++);
            carriages.add(carriage);
        }

        // Toa nằm 4
        for (int i = 0; i < request.getNumFourBerthCarriages(); i++) {
            CarriageList carriage = new CarriageList();
            carriage.setTrip(trip);
            carriage.setCompartment(fourBerthCompartment);
            carriage.setStt(stt++);
            carriages.add(carriage);
        }

        // Lưu danh sách toa
        carriageListRepository.saveAll(carriages);

        // Tạo ghế cho từng toa
        for (CarriageList carriage : carriages) {
            Compartment compartment = carriage.getCompartment();
            int seatCount = compartment.getSeatCount();
            List<Seat> seats = new ArrayList<>();

            if (compartment.getCompartmentId() == 1) { // Toa ngồi mềm
                for (int row = 1; row <= seatCount / 4; row++) {
                    for (char seatLetter : new char[]{'A', 'B', 'C', 'D'}) {
                        Seat seat = new Seat();
                        seat.setCarriageList(carriage);
                        seat.setSeatNumber(row + String.valueOf(seatLetter));
                        seat.setFloor(1);
                        seat.setSeatFactor(BigDecimal.valueOf(1.0));
                        seat.setSeatStatus("Available");
                        seats.add(seat);
                    }
                }
            } else if (compartment.getCompartmentId() == 2) { // Toa nằm 6
                for (int row = 1; row <= seatCount / 3; row++) {
                    for (int floor = 1; floor <= 3; floor++) {
                        Seat seat = new Seat();
                        seat.setCarriageList(carriage);
                        seat.setSeatNumber(row + (floor == 1 ? "A" : floor == 2 ? "B" : "C"));
                        seat.setFloor(floor);
                        if (floor == 1) {
                            seat.setSeatFactor(BigDecimal.valueOf(1.0));
                        } else if (floor == 2) {
                            seat.setSeatFactor(BigDecimal.valueOf(1.2));
                        } else {
                            seat.setSeatFactor(BigDecimal.valueOf(1.4));
                        }
                        seat.setSeatStatus("Available");
                        seats.add(seat);
                    }
                }
            } else if (compartment.getCompartmentId() == 3) { // Toa nằm 4
                for (int row = 1; row <= seatCount / 2; row++) {
                    for (int floor = 1; floor <= 2; floor++) {
                        Seat seat = new Seat();
                        seat.setCarriageList(carriage);
                        seat.setSeatNumber(row + (floor == 1 ? "A" : "B"));
                        seat.setFloor(floor);
                        if (floor == 1) {
                            seat.setSeatFactor(BigDecimal.valueOf(1.0));
                        } else {
                            seat.setSeatFactor(BigDecimal.valueOf(1.2));
                        }
                        seat.setSeatStatus("Available");
                        seats.add(seat);
                    }
                }
            }
            seatRepository.saveAll(seats);
        }

        return trip;
    }

    public TripRepository getTripRepository() {
        return tripRepository;
    }

    public void cancelTrip(int tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found for tripId: " + tripId));

        if (!trip.getTripStatus().equals("Scheduled")) {
            throw new IllegalArgumentException("Cannot cancel a trip that is not scheduled");
        }

        trip.setTripStatus("Cancelled");
        tripRepository.save(trip);

    }
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> getTripById(int id) {
        return tripRepository.findById(id);
    }


    public List<Trip> findTrips(Train train, LocalDate fromDate, LocalDate toDate) {
        return tripRepository.searchTrips(train, fromDate, toDate);
    }



}
