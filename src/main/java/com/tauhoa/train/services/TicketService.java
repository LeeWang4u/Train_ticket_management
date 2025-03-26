package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketInformationDTO;
import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.models.Ticket;
import com.tauhoa.train.models.User;
import com.tauhoa.train.repositories.TicketRepository;
import com.tauhoa.train.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(TicketInformationDTO ticketInformationDTO, User user, Passenger passenger) {
        LocalDateTime date =LocalDateTime.now();
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setPassenger(passenger);
        ticket.setTicketStatus("pending");
        ticket.setDiscount(new BigDecimal(0));
        ticket.setArrivalStation(ticketInformationDTO.getTicketReservation().getArrivalStation());
        ticket.setDepartureStation(ticketInformationDTO.getTicketReservation().getDepartureStation());
        ticket.setInvoice(null);
        ticket.setPrice(ticketInformationDTO.getPrice());
        ticket.setPurchaseTime(date);
        ticket.setSeat(ticketInformationDTO.getTicketReservation().getSeat());
        ticket.setTotalPrice(ticketInformationDTO.getTotalPrice());
        ticket.setTrip(ticketInformationDTO.getTicketReservation().getTrip());
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
    public List<Ticket> findTicketsByUserInfo(String cccd, String phone) {
        Optional<User> userOpt = Optional.empty();

        if (cccd != null && !cccd.isEmpty()) {
            userOpt = userRepository.findByCccd(cccd);
        } else if (phone != null && !phone.isEmpty()) {
            userOpt = userRepository.findByPhone(phone);
        }

        return userOpt.map(ticketRepository::findByUser).orElse(Collections.emptyList());
    }
}
