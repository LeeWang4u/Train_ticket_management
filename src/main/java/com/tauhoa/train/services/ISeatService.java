package com.tauhoa.train.services;

import com.tauhoa.train.models.Seat;

import java.util.List;
import java.util.Optional;

public interface ISeatService {
    Optional<Seat> getSeat(int id);

    List<Seat> findAllSeatByIdTrip(int idTrip);
}
