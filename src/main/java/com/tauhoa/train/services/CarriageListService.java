package com.tauhoa.train.services;

import com.tauhoa.train.models.CarriageList;
import com.tauhoa.train.repositories.CarriageListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CarriageListService implements ICarriageListService {
    private final CarriageListRepository carriageListRepository;

    public CarriageListService(CarriageListRepository carriageListRepository){
        this.carriageListRepository = carriageListRepository;
    }
    @Override
    public Optional<CarriageList> getSeatById(int id) {
        return carriageListRepository.findById(id);
    }

    @Override
    public List<CarriageList> findAllCarriageListByIdTrip(int idTrip) {
        return carriageListRepository.findAllByTripId(idTrip);
    }
}
