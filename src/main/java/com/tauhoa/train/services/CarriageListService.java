package com.tauhoa.train.services;

import com.tauhoa.train.dtos.response.CarriageResponseDTO;
import com.tauhoa.train.dtos.response.SeatResponseDTO;
import com.tauhoa.train.models.CarriageList;
import com.tauhoa.train.models.Seat;
import com.tauhoa.train.repositories.CarriageListRepository;

import com.tauhoa.train.repositories.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarriageListService implements ICarriageListService {
    private final CarriageListRepository carriageListRepository;
    private final SeatRepository seatRepository;

//    public CarriageListService(CarriageListRepository carriageListRepository){
//        this.carriageListRepository = carriageListRepository;
//    }
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
}
