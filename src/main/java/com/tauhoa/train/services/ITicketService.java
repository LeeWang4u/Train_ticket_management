package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.TicketRequestDTO;
import com.tauhoa.train.dtos.request.TicketReservationReqDTO;
import com.tauhoa.train.dtos.response.TicketResponseDTO;
import com.tauhoa.train.models.*;

import java.util.List;

public interface ITicketService {
    void save(TicketRequestDTO ticketDTO, Customer customer, Passenger passenger , ReservationCode reservationCode);
    TicketResponseDTO saveReservation(TicketReservationReqDTO ticketDTO);
    void deleteTicket(TicketReservationReqDTO ticketReservationReqDTO);
    void update(String ticketStatus);
    Ticket findByTicketId(Integer ticketId);
    List<Ticket> findByCustomer(String cccd, String phone);
}
