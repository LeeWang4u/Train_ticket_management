package com.tauhoa.train.services;

import com.tauhoa.train.models.Seat;
import com.tauhoa.train.repositories.SeatRepository;
import com.tauhoa.train.services.impl.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final SeatRepository seatRepository;
    @Override
    public Optional<Seat> getSeat(int id) {
        return seatRepository.findById(id);
    }

    @Override
    public List<Seat> findAllSeatByIdTrip(int idTrip) {
        return seatRepository.findAllSeatsByTripId(idTrip);
    }
}
