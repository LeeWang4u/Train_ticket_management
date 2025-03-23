package com.tauhoa.train.services;

import com.tauhoa.train.models.CarriageList;
import com.tauhoa.train.repositories.CarriageListRepository;
import com.tauhoa.train.services.impl.ICarriageListService;
import com.tauhoa.train.services.impl.ICompartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarriageListService implements ICarriageListService {
    private final CarriageListRepository carriageListRepository;
    @Override
    public Optional<CarriageList> getSeatById(int id) {
        return carriageListRepository.findById(id);
    }

    @Override
    public List<CarriageList> findAllCarriageListByIdTrip(int idTrip) {
        return carriageListRepository.findAllByTrip(idTrip);
    }
}
