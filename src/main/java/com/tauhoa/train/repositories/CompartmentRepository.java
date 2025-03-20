package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Compartment;
import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompartmentRepository extends JpaRepository<Compartment, Integer> {
}
