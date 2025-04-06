package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketReservationDTO;
import com.tauhoa.train.dtos.request.TicketReservationReqDTO;
import com.tauhoa.train.models.TicketReservation;

public interface ITicketReservationService {
    TicketReservation save(TicketReservationReqDTO ticketReservationDTO);
    void update(TicketReservation ticketReservation);
    void delete(TicketReservationReqDTO ticketReservationDTO);
    TicketReservation getTicketReservation(int ticketId);
}
