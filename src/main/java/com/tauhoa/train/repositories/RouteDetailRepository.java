package com.tauhoa.train.repositories;

import com.tauhoa.train.models.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteDetailRepository extends JpaRepository<TrainSchedule, Integer>{
}
