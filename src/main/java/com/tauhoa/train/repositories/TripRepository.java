package com.tauhoa.train.repositories;


import com.tauhoa.train.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
//    @Override
//    List<Trip> findAll();

    List<Trip> findAllByTripId(int tripId);

    List<Trip> findByTripDate(LocalDate tripDate);

    List<Trip> findByTripId(int tripId);
}
