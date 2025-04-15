package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
}
