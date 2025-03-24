package com.tauhoa.train.repositories;

import com.tauhoa.train.models.CarriageList;
import com.tauhoa.train.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarriageListRepository extends JpaRepository<CarriageList, Integer> {
//    List<CarriageList> findAllByTripId(int trip_id);
    @Query("SELECT cl FROM CarriageList cl WHERE cl.trip.tripId = :tripId")
    List<CarriageList> findAllByTripId(@Param("tripId") int tripId);
    @Override
    Optional<CarriageList> findById(Integer id);
}
