package com.tauhoa.train.services;

import com.tauhoa.train.dtos.TicketReservationDTO;
import com.tauhoa.train.dtos.request.TicketReservationReqDTO;
import com.tauhoa.train.models.Seat;
import com.tauhoa.train.models.Station;
import com.tauhoa.train.models.TicketReservation;
import com.tauhoa.train.models.Trip;
import com.tauhoa.train.repositories.TicketReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketReservationService implements ITicketReservationService {
    private final TicketReservationRepository ticketReservationRepository;
    private final TripService tripService;
    private final StationService stationService;
    private final SeatService seatService;
    public void update(TicketReservation ticketReservation) {
        ticketReservation.setReservationStatus("Booked");
        ticketReservationRepository.save(ticketReservation);
    }

    public TicketReservation getTicketReservation(int ticketId) {
        return ticketReservationRepository.findByReservationId(ticketId);
    }

    public TicketReservation save(TicketReservationReqDTO ticketReservationDTO) {
       LocalDateTime now = LocalDateTime.now();
       Optional<Seat> seat = seatService.getSeat(ticketReservationDTO.getSeat());
        Trip trip = tripService.findByTripId(ticketReservationDTO.getTrip());
        Optional<Station> departureStation = stationService.findByStationName(ticketReservationDTO.getDepartureStation());
        Optional<Station> arrivalStation = stationService.findByStationName(ticketReservationDTO.getArrivalStation());
        TicketReservation ticketReservation = new TicketReservation(trip,seat.get(),departureStation.get(),arrivalStation.get(),now,"OCCUPIE");
        ticketReservationRepository.save(ticketReservation);
        return ticketReservation;
    }
    public void delete(TicketReservationReqDTO ticketReservationDTO) {
        Optional<Seat> seat = seatService.getSeat(ticketReservationDTO.getSeat());
        Trip trip = tripService.findByTripId(ticketReservationDTO.getTrip());
        Optional<Station> departureStation = stationService.findByStationName(ticketReservationDTO.getDepartureStation());
        Optional<Station> arrivalStation = stationService.findByStationName(ticketReservationDTO.getArrivalStation());
        Optional<TicketReservation> ticketReservation = ticketReservationRepository.findBySeatAndTripAndDepartureStationAndArrivalStation(
                seat.get(),
                trip,
                departureStation.get(),
                arrivalStation.get()
        );
        ticketReservationRepository.delete(ticketReservation.get());
    }
}
