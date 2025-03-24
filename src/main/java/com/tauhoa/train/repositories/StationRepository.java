package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Station;
import com.tauhoa.train.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Integer> {
    List<Station> findAll();

    List<Station> findByStationId(int id);
}
