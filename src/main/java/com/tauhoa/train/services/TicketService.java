package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketInformationDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void save(TicketInformationDTO ticketInformationDTO, Customer customer, Passenger passenger, Invoice invoice) {
        LocalDateTime date =LocalDateTime.now();
        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setPassenger(passenger);
        ticket.setTicketStatus("Complete");
        ticket.setDiscount(ticketInformationDTO.getDiscount());
        ticket.setReservation(ticketInformationDTO.getTicketReservation());
        ticket.setInvoice(invoice);
        ticket.setPrice(ticketInformationDTO.getPrice());
        ticket.setPurchaseTime(date);
        ticket.setTotalPrice(ticketInformationDTO.getTotalPrice());
        ticketRepository.save(ticket);
    }

    @Override
    public void update(String ticketStatus){

    }

    @Override
    public Ticket findByTicketId(Integer ticketId){
        return null;
    }

    @Override
    public List<Ticket> findByCustomer(Customer customer){
        return null;
    }

}
