package com.tauhoa.train.services;

import com.tauhoa.train.models.Station;
import com.tauhoa.train.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class StationService implements IStationService {
    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository){
        this.stationRepository = stationRepository;
    }
    @Override
    public Optional<Station> getTrip(int id) {
        return stationRepository.findById(id);
    }
}
