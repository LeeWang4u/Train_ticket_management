package com.tauhoa.train.repositories;

import com.tauhoa.train.models.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Integer> {
    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.station.stationId = :stationId")
    List<TrainSchedule> findByStationId(@Param("stationId") int stationId);

    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.train.trainId = :trainId AND ts.station.stationId = :stationId")
    Optional<TrainSchedule> findByTrainIdAndStationId(@Param("trainId") int trainId, @Param("stationId") int stationId);

    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.train.trainId = :trainId ORDER BY ts.ordinalNumber")
    List<TrainSchedule> findByTrainId(@Param("trainId") int trainId);
}