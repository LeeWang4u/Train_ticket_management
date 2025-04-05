package com.tauhoa.train.services;


import com.tauhoa.train.dtos.response.CarriageResponseDTO;
import com.tauhoa.train.dtos.response.CarriageSeatAvailabilityResponseDTO;
import com.tauhoa.train.dtos.response.SeatResponseDTO;
import com.tauhoa.train.dtos.response.TripAvailabilityResponseDTO;
import com.tauhoa.train.models.CarriageList;

import java.util.List;
import java.util.Optional;

public interface ICarriageListService {
    Optional<CarriageList> getSeatById(int id);

    List<CarriageList> findAllCarriageListByIdTrip(int idTrip);

    List<CarriageResponseDTO> findCarriagesAndSeatsByTripId(int tripId);

    List<SeatResponseDTO> findSeatsByTripIdAndStt(int tripId, int stt);

    List<SeatResponseDTO> findSeatsByTripIdAndCarriageListId(int tripId, int carriageListId);

    CarriageSeatAvailabilityResponseDTO findSeatsAvailabilityByTripIdAndCarriageListId(
            int tripId, int carriageListId, int departureStationId, int arrivalStationId);

    TripAvailabilityResponseDTO findTripWithCarriagesAndSeatsAvailability(
            int tripId, int departureStationId, int arrivalStationId);

    TripAvailabilityResponseDTO findTripWithCarriagesAndSeatsAvailabilityByStationName(
            int tripId, String departureStation, String arrivalStation);
}
