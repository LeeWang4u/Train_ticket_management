package com.tauhoa.train.services;

import com.tauhoa.train.models.Station;

import java.util.List;
import java.util.Optional;

public interface IStationService {
//    Optional<Station> getStation(int id);
    Station getStation(int id) throws Exception;

    List<Station> findStationsByKeyword(String keyword);

    List<Station> getAllStation();
}
