package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Station;
import com.tauhoa.train.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    List<Station> findAll();
    Optional<Station> findByStationName(String stationName);
    List<Station> findByStationId(int id);




    List<Station> findByStationNameContainingIgnoreCase(String keyword);
 //   List<Station> findByNameContainingIgnoreCase(String keyword);

    List<Station> findByStationNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String keyword1, String keyword2);


}
