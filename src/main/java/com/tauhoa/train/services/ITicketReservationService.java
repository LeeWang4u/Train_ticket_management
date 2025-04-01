package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketReservationDTO;
import com.tauhoa.train.models.TicketReservation;

public interface ITicketReservationService {
    TicketReservation save(TicketReservationDTO ticketReservationDTO);
    void update(TicketReservation ticketReservation);
    void delete(TicketReservation ticketReservation);
    TicketReservation getTicketReservation(int ticketId);
}
