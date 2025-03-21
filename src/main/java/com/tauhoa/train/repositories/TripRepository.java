package com.tauhoa.train.repositories;


import com.tauhoa.train.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer> {
}
