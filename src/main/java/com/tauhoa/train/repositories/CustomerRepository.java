package com.tauhoa.train.repositories;

import com.tauhoa.train.dtos.response.CustomerTicketSummaryResponse;
import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String email);
    List<Customer> findByCccdAndPhone(String cccd, String phone);
    @Query("SELECT new com.tauhoa.train.dtos.response.CustomerTicketSummaryResponse(c.customerId, c.email, c.phone, COUNT(t)) " +
            "FROM Customer c LEFT JOIN c.tickets t " +
            "GROUP BY c.customerId, c.email, c.phone")
    List<CustomerTicketSummaryResponse> fetchCustomerTicketSummary();
}
