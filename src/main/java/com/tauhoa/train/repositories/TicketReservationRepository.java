package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Seat;
import com.tauhoa.train.models.TicketReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface TicketReservationRepository extends JpaRepository<TicketReservation, Integer>{
    TicketReservation findByReservationId(int ticketReservationId);

    List<TicketReservation> findBySeatSeatId(int seatId);

    @Query("SELECT tr FROM TicketReservation tr WHERE tr.trip.tripId = :tripId")
    List<TicketReservation> findByTripId(@Param("tripId") int tripId);
}
