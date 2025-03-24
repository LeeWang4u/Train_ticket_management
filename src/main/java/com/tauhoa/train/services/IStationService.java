package com.tauhoa.train.services;

import com.tauhoa.train.models.Station;

import java.util.Optional;

public interface IStationService {
    Optional<Station> getTrip(int id);
}
