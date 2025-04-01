package com.tauhoa.train.services;


import com.tauhoa.train.dtos.response.CarriageResponseDTO;
import com.tauhoa.train.dtos.response.SeatResponseDTO;
import com.tauhoa.train.models.CarriageList;

import java.util.List;
import java.util.Optional;

public interface ICarriageListService {
    Optional<CarriageList> getSeatById(int id);

    List<CarriageList> findAllCarriageListByIdTrip(int idTrip);

    List<CarriageResponseDTO> findCarriagesAndSeatsByTripId(int tripId);

    List<SeatResponseDTO> findSeatsByTripIdAndStt(int tripId, int stt);

    List<SeatResponseDTO> findSeatsByTripIdAndCarriageListId(int tripId, int carriageListId);
}
