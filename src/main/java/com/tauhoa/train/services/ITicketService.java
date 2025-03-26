package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketInformationDTO;
import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.models.Ticket;
import com.tauhoa.train.models.User;

import java.util.List;

public interface ITicketService {
    void save(TicketInformationDTO ticketDTO, User user, Passenger passenger);
    void update(String ticketStatus);
    Ticket findByTicketId(Integer ticketId);
    List<Ticket> findByUser(User user);
}
