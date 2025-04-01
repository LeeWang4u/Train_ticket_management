package com.tauhoa.train.repositories;

import com.tauhoa.train.models.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Integer> {
    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.station.stationId = :stationId")
    List<TrainSchedule> findByStationId(@Param("stationId") int stationId);
}