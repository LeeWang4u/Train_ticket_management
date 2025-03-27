package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.Ticket;
import com.tauhoa.train.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    Ticket findByTicketId(Integer ticketId);
    List<Ticket> findByCustomer(Customer customer);
}
