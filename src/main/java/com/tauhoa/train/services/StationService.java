package com.tauhoa.train.services;

import com.tauhoa.train.exceptions.DataNotFoundException;
import com.tauhoa.train.models.Station;
import com.tauhoa.train.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService implements IStationService {
    private final StationRepository stationRepository;

//    public StationService(StationRepository stationRepository){
//        this.stationRepository = stationRepository;
//    }
    @Override
    public Station getStation(int id) throws Exception{
        Optional<Station>  optionalStation = stationRepository.findById(id);
        if(optionalStation.isPresent()) {
            return optionalStation.get();
        }
       throw new DataNotFoundException("Cannot find station with id =" + id);

    }

    @Override
    public List<Station> findStationsByKeyword(String keyword) {
//        return stationRepository.findByStationNameContainingIgnoreCase(keyword);
        return stationRepository.findByStationNameContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword);

    }
//    public List<Station> getStation(int id) {
//        return stationRepository.findByStationId(id);
//    }

}
