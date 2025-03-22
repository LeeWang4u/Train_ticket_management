package com.tauhoa.train.services;

import com.tauhoa.train.models.Trip;
import com.tauhoa.train.repositories.TripRepository;
import com.tauhoa.train.services.impl.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService implements ITripService {
    private final TripRepository tripRepository;

    @Override
    public Optional<Trip> getTrip(int id) {
        return tripRepository.findById(id);
    }

    @Override
    public List<Trip> findByTripDate(LocalDate tripDate) {
        return tripRepository.findByTripDate(tripDate);
    }


}
