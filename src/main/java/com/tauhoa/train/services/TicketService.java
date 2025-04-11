package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketInformationDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.CustomerRepository;
import com.tauhoa.train.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private final CustomerRepository customerRepository;

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
    public Ticket findByTicketId(Integer ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé với ID: " + ticketId));
    }

    @Override
    public List<Ticket> findByCustomer(String cccd, String phone) {
        if (cccd == null || cccd.isEmpty() || phone == null || phone.isEmpty()) {
            return Collections.emptyList();
        }

        List<Customer> customers = customerRepository.findByCccdAndPhone(cccd, phone);
        if (customers.isEmpty()) {
            return Collections.emptyList();
        }

        List<Ticket> allTickets = new ArrayList<>();
        for (Customer customer : customers) {
            allTickets.addAll(ticketRepository.findByCustomer(customer));
        }

        return allTickets;
    }


}
