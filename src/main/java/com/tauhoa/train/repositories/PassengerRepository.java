package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
}
