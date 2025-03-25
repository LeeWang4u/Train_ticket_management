package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketReservationDTO;
import com.tauhoa.train.models.TicketReservation;
import com.tauhoa.train.repositories.TicketReservationRepository;
import com.tauhoa.train.services.impl.ITicketReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketReservationService implements ITicketReservationService {
    private final TicketReservationRepository ticketReservationRepository;
    public void update(TicketReservation ticketReservation) {
        ticketReservation.setReservationStatus("Booked");
        ticketReservationRepository.save(ticketReservation);
    }
}
