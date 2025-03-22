package com.tauhoa.train.services.impl;

import com.tauhoa.train.models.Trip;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
public interface ITripService {
    Optional<Trip> getTrip(int id);

    List<Trip> findByTripDate(LocalDate tripDate);
}
