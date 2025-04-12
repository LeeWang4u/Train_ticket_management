package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Seat;
import com.tauhoa.train.models.Station;
import com.tauhoa.train.models.TicketReservation;
import com.tauhoa.train.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketReservationRepository extends JpaRepository<TicketReservation, Integer>{
    TicketReservation findByReservationId(int ticketReservationId);

    List<TicketReservation> findBySeatSeatId(int seatId);
    Optional<TicketReservation> findBySeatAndTripAndDepartureStationAndArrivalStation(
            Seat seat,
            Trip trip,
            Station departureStation,
            Station arrivalStation
    );
    @Query("SELECT tr FROM TicketReservation tr WHERE tr.trip.tripId = :tripId")
    List<TicketReservation> findByTripId(@Param("tripId") int tripId);
}
