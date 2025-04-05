package com.tauhoa.train.services;

import com.tauhoa.train.dtos.response.*;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarriageListService implements ICarriageListService {
    private final CarriageListRepository carriageListRepository;
    private final SeatRepository seatRepository;
    private final TicketReservationRepository ticketReservationRepository;
    private final TrainScheduleRepository trainScheduleRepository;
    private final TripRepository tripRepository;
    private final StationRepository stationRepository;


    @Override
    public Optional<CarriageList> getSeatById(int id) {
        return carriageListRepository.findById(id);
    }

    @Override
    public List<CarriageList> findAllCarriageListByIdTrip(int idTrip) {
        return carriageListRepository.findAllByTripId(idTrip);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarriageResponseDTO> findCarriagesAndSeatsByTripId(int tripId) {
        // Lấy danh sách toa
        List<CarriageList> carriages = carriageListRepository.findByTripId(tripId);

        // Chuyển đổi sang DTO
        return carriages.stream()
                .map(carriage -> {
                    // Lấy danh sách ghế của toa
                    List<Seat> seats = seatRepository.findByCarriageListId(carriage.getCarriageListId());

                    // Chuyển đổi danh sách ghế sang DTO
                    List<SeatResponseDTO> seatDTOs = seats.stream()
                            .map(seat -> new SeatResponseDTO(
                                    seat.getSeatId(),
                                    seat.getSeatNumber(),
                                    seat.getFloor(),
                                    seat.getSeatFactor(),
                                    seat.getSeatStatus()
                            ))
                            .collect(Collectors.toList());

                    // Tạo DTO cho toa
                    return new CarriageResponseDTO(
                            carriage.getCarriageListId(),
                            carriage.getStt(),
                            carriage.getCompartment().getCompartmentName(),
                            carriage.getCompartment().getClassFactor(),
                            carriage.getCompartment().getSeatCount(),
                            seatDTOs
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponseDTO> findSeatsByTripIdAndStt(int tripId, int stt) {
        // Tìm CarriageList theo tripId và stt
        CarriageList carriage = carriageListRepository.findByTripIdAndStt(tripId, stt)
                .orElseThrow(() -> new IllegalArgumentException("No carriage found for tripId: " + tripId + " and stt: " + stt));

        // Lấy danh sách ghế của toa
        List<Seat> seats = seatRepository.findByCarriageListId(carriage.getCarriageListId());

        // Chuyển đổi sang DTO
        return seats.stream()
                .map(seat -> new SeatResponseDTO(
                        seat.getSeatId(),
                        seat.getSeatNumber(),
                        seat.getFloor(),
                        seat.getSeatFactor(),
                        seat.getSeatStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatResponseDTO> findSeatsByTripIdAndCarriageListId(int tripId, int carriageListId) {
        // Kiểm tra CarriageList có tồn tại và thuộc tripId
        CarriageList carriage = carriageListRepository.findByTripIdAndCarriageListId(tripId, carriageListId)
                .orElseThrow(() -> new IllegalArgumentException("No carriage found for tripId: " + tripId + " and carriageListId: " + carriageListId));

        // Lấy danh sách ghế của toa
        List<Seat> seats = seatRepository.findByCarriageListId(carriage.getCarriageListId());

        // Chuyển đổi sang DTO
        return seats.stream()
                .map(seat -> new SeatResponseDTO(
                        seat.getSeatId(),
                        seat.getSeatNumber(),
                        seat.getFloor(),
                        seat.getSeatFactor(),
                        seat.getSeatStatus()
                ))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public CarriageSeatAvailabilityResponseDTO findSeatsAvailabilityByTripIdAndCarriageListId(
            int tripId, int carriageListId, int departureStationId, int arrivalStationId) {
        // Lấy thông tin Trip để có base_price
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found for tripId: " + tripId));
        BigDecimal basePrice = trip.getBasePrice();

        // Kiểm tra CarriageList có tồn tại và thuộc tripId
        CarriageList carriage = carriageListRepository.findByTripIdAndCarriageListId(tripId, carriageListId)
                .orElseThrow(() -> new IllegalArgumentException("No carriage found for tripId: " + tripId + " and carriageListId: " + carriageListId));

        // Lấy danh sách ghế của toa
        List<Seat> seats = seatRepository.findByCarriageListId(carriage.getCarriageListId());

        // Lấy danh sách vé của chuyến tàu
        List<TicketReservation> reservations = ticketReservationRepository.findByTripId(tripId);

        // Lấy ordinalNumber và distance của ga đi và ga đến trong yêu cầu
        TrainSchedule departureSchedule = trainScheduleRepository.findByTrainIdAndStationId(carriage.getTrip().getTrain().getTrainId(), departureStationId)
                .orElseThrow(() -> new IllegalArgumentException("Departure station not found in train schedule"));
        TrainSchedule arrivalSchedule = trainScheduleRepository.findByTrainIdAndStationId(carriage.getTrip().getTrain().getTrainId(), arrivalStationId)
                .orElseThrow(() -> new IllegalArgumentException("Arrival station not found in train schedule"));

        int requestDepartureOrdinal = departureSchedule.getOrdinalNumber();
        int requestArrivalOrdinal = arrivalSchedule.getOrdinalNumber();
        BigDecimal distance = arrivalSchedule.getDistance().subtract(departureSchedule.getDistance()); // Tính khoảng cách

        if (requestDepartureOrdinal >= requestArrivalOrdinal) {
            throw new IllegalArgumentException("Departure station must be before arrival station");
        }

        if (distance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid distance calculation: distance cannot be negative");
        }

        // Chuyển đổi danh sách ghế sang DTO với trạng thái khả dụng và giá vé
        List<SeatAvailabilityResponseDTO> seatDTOs = seats.stream()
                .map(seat -> {
                    // Kiểm tra xem ghế có bị chiếm bởi vé nào không
                    boolean isAvailable = reservations.stream()
                            .filter(res -> res.getSeat().getSeatId() == seat.getSeatId())
                            .noneMatch(res -> {
                                TrainSchedule resDepartureSchedule = trainScheduleRepository
                                        .findByTrainIdAndStationId(carriage.getTrip().getTrain().getTrainId(), res.getDepartureStation().getStationId())
                                        .orElseThrow(() -> new IllegalStateException("Invalid reservation data"));
                                TrainSchedule resArrivalSchedule = trainScheduleRepository
                                        .findByTrainIdAndStationId(carriage.getTrip().getTrain().getTrainId(), res.getArrivalStation().getStationId())
                                        .orElseThrow(() -> new IllegalStateException("Invalid reservation data"));

                                int resDepartureOrdinal = resDepartureSchedule.getOrdinalNumber();
                                int resArrivalOrdinal = resArrivalSchedule.getOrdinalNumber();

                                // Ghế không khả dụng nếu hành trình yêu cầu chồng lấn với vé
                                return resDepartureOrdinal < requestArrivalOrdinal && resArrivalOrdinal > requestDepartureOrdinal;
                            });

                    // Xác định trạng thái ghế trong phản hồi dựa trên isAvailable
                    String status = isAvailable ? "AVAILABLE" : "BOOKED";

                    // Tính giá vé: base_price * distance * class_factor * seat_factor
                    BigDecimal classFactor = carriage.getCompartment().getClassFactor();
                    BigDecimal seatFactor = seat.getSeatFactor();
                    BigDecimal ticketPrice = basePrice
                            .multiply(distance)
                            .multiply(classFactor)
                            .multiply(seatFactor);

                    return new SeatAvailabilityResponseDTO(
                            seat.getSeatId(),
                            seat.getSeatNumber(),
                            seat.getFloor(),
                            seat.getSeatFactor(),
                            status,
                            isAvailable,
                            ticketPrice // Thêm giá vé
                    );
                })
                .collect(Collectors.toList());

        // Tạo DTO cho toa
        return new CarriageSeatAvailabilityResponseDTO(
                carriage.getCarriageListId(),
                carriage.getStt(),
                carriage.getCompartment().getCompartmentName(),
                carriage.getCompartment().getClassFactor(),
                carriage.getCompartment().getSeatCount(),
                seatDTOs
        );
    }


    @Override
    @Transactional(readOnly = true)
    public TripAvailabilityResponseDTO findTripWithCarriagesAndSeatsAvailability(
            int tripId, int departureStationId, int arrivalStationId) {
        // Lấy thông tin Trip để có base_price
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found for tripId: " + tripId));
        BigDecimal basePrice = trip.getBasePrice();

        // Lấy danh sách toa của chuyến tàu
        List<CarriageList> carriages = carriageListRepository.findByTripId(tripId);
        if (carriages.isEmpty()) {
            throw new IllegalArgumentException("No carriages found for tripId: " + tripId);
        }

        // Lấy danh sách vé của chuyến tàu
        List<TicketReservation> reservations = ticketReservationRepository.findByTripId(tripId);

        // Lấy ordinalNumber và distance của ga đi và ga đến trong yêu cầu
        int trainId = carriages.get(0).getTrip().getTrain().getTrainId();
        TrainSchedule departureSchedule = trainScheduleRepository.findByTrainIdAndStationId(trainId, departureStationId)
                .orElseThrow(() -> new IllegalArgumentException("Departure station not found in train schedule"));
        TrainSchedule arrivalSchedule = trainScheduleRepository.findByTrainIdAndStationId(trainId, arrivalStationId)
                .orElseThrow(() -> new IllegalArgumentException("Arrival station not found in train schedule"));

        int requestDepartureOrdinal = departureSchedule.getOrdinalNumber();
        int requestArrivalOrdinal = arrivalSchedule.getOrdinalNumber();
        BigDecimal distance = arrivalSchedule.getDistance().subtract(departureSchedule.getDistance()); // Tính khoảng cách

        if (requestDepartureOrdinal >= requestArrivalOrdinal) {
            throw new IllegalArgumentException("Departure station must be before arrival station");
        }

        if (distance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid distance calculation: distance cannot be negative");
        }

        // Chuyển đổi danh sách toa và ghế
        List<CarriageAvailabilityResponseDTO> carriageDTOs = carriages.stream()
                .map(carriage -> {
                    // Lấy danh sách ghế của toa
                    List<Seat> seats = seatRepository.findByCarriageListId(carriage.getCarriageListId());

                    // Chuyển đổi danh sách ghế với trạng thái khả dụng và giá vé
                    List<SeatAvailabilityResponseDTO> seatDTOs = seats.stream()
                            .map(seat -> {
                                // Kiểm tra xem ghế có bị chiếm bởi vé nào không
                                boolean isAvailable = reservations.stream()
                                        .filter(res -> res.getSeat().getSeatId() == seat.getSeatId())
                                        .noneMatch(res -> {
                                            TrainSchedule resDepartureSchedule = trainScheduleRepository
                                                    .findByTrainIdAndStationId(trainId, res.getDepartureStation().getStationId())
                                                    .orElseThrow(() -> new IllegalStateException("Invalid reservation data"));
                                            TrainSchedule resArrivalSchedule = trainScheduleRepository
                                                    .findByTrainIdAndStationId(trainId, res.getArrivalStation().getStationId())
                                                    .orElseThrow(() -> new IllegalStateException("Invalid reservation data"));

                                            int resDepartureOrdinal = resDepartureSchedule.getOrdinalNumber();
                                            int resArrivalOrdinal = resArrivalSchedule.getOrdinalNumber();

                                            return resDepartureOrdinal < requestArrivalOrdinal && resArrivalOrdinal > requestDepartureOrdinal;
                                        });

                                // Xác định trạng thái ghế
                                String status = isAvailable ? "AVAILABLE" : "BOOKED";

                                // Tính giá vé: base_price * distance * class_factor * seat_factor
                                BigDecimal classFactor = carriage.getCompartment().getClassFactor();
                                BigDecimal seatFactor = seat.getSeatFactor();
                                BigDecimal ticketPrice = basePrice
                                        .multiply(distance)
                                        .multiply(classFactor)
                                        .multiply(seatFactor);

                                return new SeatAvailabilityResponseDTO(
                                        seat.getSeatId(),
                                        seat.getSeatNumber(),
                                        seat.getFloor(),
                                        seat.getSeatFactor(),
                                        status,
                                        isAvailable,
                                        ticketPrice // Thêm giá vé
                                );
                            })
                            .collect(Collectors.toList());

                    // Tạo DTO cho toa
                    return new CarriageAvailabilityResponseDTO(
                            carriage.getCarriageListId(),
                            carriage.getStt(),
                            carriage.getCompartment().getCompartmentName(),
                            carriage.getCompartment().getClassFactor(),
                            carriage.getCompartment().getSeatCount(),
                            seatDTOs
                    );
                })
                .collect(Collectors.toList());

        // Tạo DTO cho chuyến tàu
        return new TripAvailabilityResponseDTO(tripId, carriageDTOs);
    }


    @Override
    @Transactional(readOnly = true)
    public TripAvailabilityResponseDTO findTripWithCarriagesAndSeatsAvailabilityByStationName(
            int tripId, String departureStationName, String arrivalStationName) {
        // Lấy thông tin Trip để có base_price
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found for tripId: " + tripId));
        BigDecimal basePrice = trip.getBasePrice();

        // Lấy station_id từ station_name
        Station departureStationEntity = stationRepository.findByStationName(departureStationName)
                .orElseThrow(() -> new IllegalArgumentException("Departure station not found: " + departureStationName));
        Station arrivalStationEntity = stationRepository.findByStationName(arrivalStationName)
                .orElseThrow(() -> new IllegalArgumentException("Arrival station not found: " + arrivalStationName));

        int departureStationId = departureStationEntity.getStationId();
        int arrivalStationId = arrivalStationEntity.getStationId();

        // Lấy danh sách toa của chuyến tàu
        List<CarriageList> carriages = carriageListRepository.findByTripId(tripId);
        if (carriages.isEmpty()) {
            throw new IllegalArgumentException("No carriages found for tripId: " + tripId);
        }

        // Lấy danh sách vé của chuyến tàu
        List<TicketReservation> reservations = ticketReservationRepository.findByTripId(tripId);

        // Lấy ordinalNumber và distance của ga đi và ga đến trong yêu cầu
        int trainId = carriages.get(0).getTrip().getTrain().getTrainId();
        TrainSchedule departureSchedule = trainScheduleRepository.findByTrainIdAndStationId(trainId, departureStationId)
                .orElseThrow(() -> new IllegalArgumentException("Departure station not found in train schedule"));
        TrainSchedule arrivalSchedule = trainScheduleRepository.findByTrainIdAndStationId(trainId, arrivalStationId)
                .orElseThrow(() -> new IllegalArgumentException("Arrival station not found in train schedule"));

        int requestDepartureOrdinal = departureSchedule.getOrdinalNumber();
        int requestArrivalOrdinal = arrivalSchedule.getOrdinalNumber();
        BigDecimal distance = arrivalSchedule.getDistance().subtract(departureSchedule.getDistance()); // Tính khoảng cách

        if (requestDepartureOrdinal >= requestArrivalOrdinal) {
            throw new IllegalArgumentException("Departure station must be before arrival station");
        }

        if (distance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid distance calculation: distance cannot be negative");
        }

        // Chuyển đổi danh sách toa và ghế
        List<CarriageAvailabilityResponseDTO> carriageDTOs = carriages.stream()
                .map(carriage -> {
                    // Lấy danh sách ghế của toa
                    List<Seat> seats = seatRepository.findByCarriageListId(carriage.getCarriageListId());

                    // Chuyển đổi danh sách ghế với trạng thái khả dụng và giá vé
                    List<SeatAvailabilityResponseDTO> seatDTOs = seats.stream()
                            .map(seat -> {
                                // Kiểm tra xem ghế có bị chiếm bởi vé nào không
                                boolean isAvailable = reservations.stream()
                                        .filter(res -> res.getSeat().getSeatId() == seat.getSeatId())
                                        .noneMatch(res -> {
                                            TrainSchedule resDepartureSchedule = trainScheduleRepository
                                                    .findByTrainIdAndStationId(trainId, res.getDepartureStation().getStationId())
                                                    .orElseThrow(() -> new IllegalStateException("Invalid reservation data"));
                                            TrainSchedule resArrivalSchedule = trainScheduleRepository
                                                    .findByTrainIdAndStationId(trainId, res.getArrivalStation().getStationId())
                                                    .orElseThrow(() -> new IllegalStateException("Invalid reservation data"));

                                            int resDepartureOrdinal = resDepartureSchedule.getOrdinalNumber();
                                            int resArrivalOrdinal = resArrivalSchedule.getOrdinalNumber();

                                            return resDepartureOrdinal < requestArrivalOrdinal && resArrivalOrdinal > requestDepartureOrdinal;
                                        });

                                // Xác định trạng thái ghế
                                String status = isAvailable ? "AVAILABLE" : "BOOKED";

                                // Tính giá vé: base_price * distance * class_factor * seat_factor
                                BigDecimal classFactor = carriage.getCompartment().getClassFactor();
                                BigDecimal seatFactor = seat.getSeatFactor();
                                BigDecimal ticketPrice = basePrice
                                        .multiply(distance)
                                        .multiply(classFactor)
                                        .multiply(seatFactor);

                                return new SeatAvailabilityResponseDTO(
                                        seat.getSeatId(),
                                        seat.getSeatNumber(),
                                        seat.getFloor(),
                                        seat.getSeatFactor(),
                                        status,
                                        isAvailable,
                                        ticketPrice // Thêm giá vé
                                );
                            })
                            .collect(Collectors.toList());

                    // Tạo DTO cho toa
                    return new CarriageAvailabilityResponseDTO(
                            carriage.getCarriageListId(),
                            carriage.getStt(),
                            carriage.getCompartment().getCompartmentName(),
                            carriage.getCompartment().getClassFactor(),
                            carriage.getCompartment().getSeatCount(),
                            seatDTOs
                    );
                })
                .collect(Collectors.toList());

        // Tạo DTO cho chuyến tàu
        return new TripAvailabilityResponseDTO(tripId, carriageDTOs);
    }
}
