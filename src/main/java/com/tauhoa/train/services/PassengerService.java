package com.tauhoa.train.services;

import com.tauhoa.train.dtos.PassengerDTO;
import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.repositories.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PassengerService implements IPassengerService {
    private final PassengerRepository passengerRepository;
    @Override
    public Passenger save(PassengerDTO passengerDTO){
        LocalDateTime date =LocalDateTime.now();

        Passenger passenger = new Passenger(passengerDTO.getFullName(),passengerDTO.getCccd(),date,passengerDTO.getTicketType());
        return passengerRepository.save(passenger);
    }
}
