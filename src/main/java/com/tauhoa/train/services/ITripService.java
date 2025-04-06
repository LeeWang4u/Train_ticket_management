package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.TrainInfoDTO;
import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.models.Trip;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
public interface ITripService {
    Optional<Trip> getTrip(int id);

    List<Trip> findByTripDate(LocalDate tripDate);

    List<TrainInfoDTO> getTrainInfoByStationAndDate(String stationName, LocalDate date);

//    List<TripResponseDTO> findTrips(String departureStation, String arrivalStation, LocalDate tripDate);

    List<TripResponseDTO> findTripsByStationsAndDate(TripSearchRequestDTO request);

    Trip findByTripId(int tripId);
}
