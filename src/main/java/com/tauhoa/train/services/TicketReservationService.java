package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketReservationDTO;
import com.tauhoa.train.models.TicketReservation;
import com.tauhoa.train.repositories.TicketReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketReservationService implements ITicketReservationService {
    private final TicketReservationRepository ticketReservationRepository;
    public void update(TicketReservation ticketReservation) {
        ticketReservation.setReservationStatus("Booked");
        ticketReservationRepository.save(ticketReservation);
    }

    public TicketReservation getTicketReservation(int ticketId) {
        return ticketReservationRepository.findByReservationId(ticketId);
    }

    public TicketReservation save(TicketReservationDTO ticketReservationDTO) {
       LocalDateTime now = LocalDateTime.now();
        TicketReservation ticketReservation = new TicketReservation(ticketReservationDTO.getTrip(),ticketReservationDTO.getSeat(),ticketReservationDTO.getDepartureStationId(),ticketReservationDTO.getArrivalStationId(),now,ticketReservationDTO.getReservationStatus());
        ticketReservationRepository.save(ticketReservation);
        return ticketReservation;
    }
    public void delete(TicketReservation ticketReservation) {
        ticketReservationRepository.delete(ticketReservation);
    }
}
