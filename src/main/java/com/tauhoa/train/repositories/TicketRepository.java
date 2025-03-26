package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Ticket;
import com.tauhoa.train.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Ticket> findById(Integer ticketId);
    List<Ticket> findByUser(User user);
}


