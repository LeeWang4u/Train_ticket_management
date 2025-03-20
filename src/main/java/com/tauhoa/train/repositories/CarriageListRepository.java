package com.tauhoa.train.repositories;

import com.tauhoa.train.models.CarriageList;
import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarriageListRepository extends JpaRepository<CarriageList, Integer> {
}
