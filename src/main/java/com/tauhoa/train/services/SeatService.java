package com.tauhoa.train.services;

import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final SeatRepository seatRepository;
    private final StationRepository stationRepository;
    private final TrainScheduleRepository trainScheduleRepository;
    private final CarriageListRepository carriageListRepository;
    private final TrainRepository trainRepository;
    private final CompartmentRepository compartmentRepository;
    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;


    public SeatService(SeatRepository seatRepository, StationRepository stationRepository, TrainScheduleRepository trainScheduleRepository, CarriageListRepository carriageListRepository, TrainRepository trainRepository, CompartmentRepository compartmentRepository, TicketRepository ticketRepository, TripRepository tripRepository){
        this.seatRepository = seatRepository;
        this.stationRepository = stationRepository;
        this.trainScheduleRepository = trainScheduleRepository;
        this.carriageListRepository = carriageListRepository;
        this.trainRepository = trainRepository;
        this.compartmentRepository = compartmentRepository;
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
    }
    @Override
    public Optional<Seat> getSeat(int id) {
        return seatRepository.findById(id);
    }

    @Override
    public List<Seat> findAllSeatByIdTrip(int idTrip) {
        return seatRepository.findAllSeatsByTripId(idTrip);
    }

    @Override
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
}
