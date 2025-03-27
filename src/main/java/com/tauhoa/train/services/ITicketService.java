package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketInformationDTO;
import com.tauhoa.train.models.*;

import java.util.List;

public interface ITicketService {
    void save(TicketInformationDTO ticketDTO, Customer customer, Passenger passenger, Invoice invoice);
    void update(String ticketStatus);
    Ticket findByTicketId(Integer ticketId);
    List<Ticket> findByCustomer(Customer customer);
}
