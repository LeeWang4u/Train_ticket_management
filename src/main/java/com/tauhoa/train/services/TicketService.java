package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.TicketRequestDTO;
import com.tauhoa.train.dtos.request.TicketReservationReqDTO;
import com.tauhoa.train.dtos.response.DailySalesResponseDTO;
import com.tauhoa.train.dtos.response.MonthlySalesResponseDTO;
import com.tauhoa.train.dtos.response.TicketCountResponseDTO;
import com.tauhoa.train.dtos.response.TicketResponseDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.CustomerRepository;
import com.tauhoa.train.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final TripService tripService;
    private final SeatService seatService;
    private final StationService stationService;
    private final EmailService emailService;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    @Override
    public void save(TicketRequestDTO ticketDTO, Customer customer, Passenger passenger, ReservationCode reservationCode) {
        Ticket ticket = ticketRepository.findByTicketId(ticketDTO.getTicketId());
        System.out.println("ticket"+ticketDTO.getTicketId());
        LocalDateTime date =LocalDateTime.now();
        ticket.setCustomer(customer);
        ticket.setPassenger(passenger);
        ticket.setTicketStatus("Booked");
        ticket.setDiscount(ticketDTO.getDiscount());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setPurchaseTime(date);
        ticket.setTotalPrice(ticketDTO.getTotalPrice());
        ticket.setReservationCode(reservationCode);
        ticketRepository.save(ticket);
    }
    @Override
    public TicketResponseDTO saveReservation(TicketReservationReqDTO ticketDTO) {
        LocalDateTime date =LocalDateTime.now();
       Trip trip = tripService.findByTripId(ticketDTO.getTrip());
       Optional<Station> departureStation = stationService.findByStationName(ticketDTO.getDepartureStation());
       Optional<Station> arrivalStation = stationService.findByStationName(ticketDTO.getArrivalStation());
       Optional<Seat> seat = seatService.getSeat(ticketDTO.getSeat());
        Ticket ticket = new Ticket();
        ticket.setTicketStatus("Hold");
        ticket.setSeat(seat.get());
        ticket.setTrip(trip);
        ticket.setDepartureStation(departureStation.get());
        ticket.setArrivalStation(arrivalStation.get());
        ticket.setPurchaseTime(date);
        ticketRepository.save(ticket);
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO(ticket.getTicketId(),ticketDTO.getTrip(),ticketDTO.getDepartureStation(),ticketDTO.getArrivalStation(),ticketDTO.getSeat(),trip.getTrain().getTrainName(),trip.getTrain().getRoute().getRouteName());
        return ticketResponseDTO;
    }

    @Override
    public void deleteTicket(TicketReservationReqDTO ticketReservationReqDTO) {
        Optional<Seat> seat = seatService.getSeat(ticketReservationReqDTO.getSeat());
        Trip trip = tripService.findByTripId(ticketReservationReqDTO.getTrip());
        Optional<Station> departureStation = stationService.findByStationName(ticketReservationReqDTO.getDepartureStation());
        Optional<Station> arrivalStation = stationService.findByStationName(ticketReservationReqDTO.getArrivalStation());

        Optional<Ticket> ticket = ticketRepository.findBySeatAndTripAndDepartureStationAndArrivalStation(seat.get(), trip, departureStation.get(), arrivalStation.get());

        if (ticket != null) {
            ticketRepository.delete(ticket.get());
        } else {
            throw new RuntimeException("Không tìm thấy vé với ID: " );
        }
    }

    @Override
    public void update(String ticketStatus){

    }

    @Override
    public Ticket findByTicketId(Integer ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé với ID: " + ticketId));
    }

    public List<Ticket> findAllByTripId(int TripId){
        if (TripId <= 0) return Collections.emptyList();
        return ticketRepository.findAllByTripId(TripId);
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
    @Override
    public List<Ticket> findByReservationCode(int reservationCode) {
        return ticketRepository.findByReservationCodeReservationCodeId(reservationCode);
    }

    @Override
    public void cancelTicketByTrip(int tripId) {
        List<Ticket> tickets = ticketRepository.findByTripIdAndStatusBookedOrHold(tripId);
        for (Ticket ticket : tickets) {
            if (ticket.getTicketStatus().equals("Booked")) {
                ticket.setTicketStatus("Canceled");
                ticketRepository.save(ticket);
                emailService.sendEmailCancelTicket(ticket);
            } else {
                ticketRepository.delete(ticket);
            }

        }
    }

    public List<TicketResponseDTO> getTicketsBetween(LocalDateTime start, LocalDateTime end) {
        List<Ticket> tickets = ticketRepository.findTicketsByDateRange(start, end);
        return tickets.stream()
                .map(TicketResponseDTO::toTicketResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketCountResponseDTO> getTicketCountGroupedByStations() {
        return ticketRepository.findTicketCountGroupedByStations();
    }

    @Override
    public List<MonthlySalesResponseDTO> getMonthlySales(LocalDateTime from, LocalDateTime to) {
        return ticketRepository.getMonthlySalesByPurchaseTime(from, to);
    }

    @Override
    public List<DailySalesResponseDTO> getDailySales(LocalDateTime from, LocalDateTime to) {
        return ticketRepository.getDailySalesByPurchaseTime(from, to);
    }

    public BigDecimal getTotalRevenue() {
        return ticketRepository.getTotalPriceSum();
    }
}
