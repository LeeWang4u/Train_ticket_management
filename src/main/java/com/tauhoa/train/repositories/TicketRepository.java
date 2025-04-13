package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.Ticket;
import com.tauhoa.train.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByCustomer(Customer customer);

    @Query("SELECT t FROM Ticket t WHERE t.trip.tripId = :tripId AND t.ticketStatus IN ('Booked', 'Hold')")
    List<Ticket> findByTripIdAndStatusBookedOrHold(@Param("tripId") int tripId);


}
