package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.PassengerDTO;
import com.tauhoa.train.models.Passenger;

public interface IPassengerService {
    Passenger save(PassengerDTO passengerDTO);
//    void detele(int id);
//    void update(PassengerDTO passengerDTO);
}
