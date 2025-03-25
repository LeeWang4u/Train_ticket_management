package com.tauhoa.train.services;

import com.tauhoa.train.models.Seat;
import com.tauhoa.train.repositories.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }
    @Override
    public Optional<Seat> getSeat(int id) {
        return seatRepository.findById(id);
    }

    @Override
    public List<Seat> findAllSeatByIdTrip(int idTrip) {
        return seatRepository.findAllSeatsByTripId(idTrip);
    }
}
