package com.tauhoa.train.services.impl;

import com.tauhoa.train.dtos.PassengerDTO;
import com.tauhoa.train.models.Passenger;

public interface IPassengerService {
    Passenger save(PassengerDTO passengerDTO);
//    void detele(int id);
//    void update(PassengerDTO passengerDTO);
}
