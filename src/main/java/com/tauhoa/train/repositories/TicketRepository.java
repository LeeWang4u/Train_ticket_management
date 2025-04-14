package com.tauhoa.train.repositories;

import com.tauhoa.train.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByCustomer(Customer customer);

    Ticket findByTicketId(int ticketId);

    @Query("SELECT t FROM Ticket t WHERE t.trip.tripId = :tripId AND t.ticketStatus IN ('Booked', 'Hold')")
    List<Ticket> findByTripIdAndStatusBookedOrHold(@Param("tripId") int tripId);

    Optional<Ticket> findBySeatAndTripAndDepartureStationAndArrivalStation(
            Seat seat,
            Trip trip,
            Station departureStation,
            Station arrivalStation
    );


    List<Ticket> findBySeatSeatId(int seatId);
}
