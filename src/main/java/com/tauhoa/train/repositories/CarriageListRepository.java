package com.tauhoa.train.repositories;

import com.tauhoa.train.models.CarriageList;
import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarriageListRepository extends JpaRepository<CarriageList, Integer> {
    List<CarriageList> findAllByTrip(int trip_id);

    @Override
    Optional<CarriageList> findById(Integer integer);
}
