package com.tauhoa.train.controllers;

import com.tauhoa.train.models.Customer;
import com.tauhoa.train.repositories.CustomerRepository;
import com.tauhoa.train.dtos.response.CustomerTicketSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    // 🔹 Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/ticket-summary")
    public ResponseEntity<?> getCustomerTicketSummary() {
        try {
            List<CustomerTicketSummaryResponse> summaryList = customerRepository.fetchCustomerTicketSummary();
            if (summaryList.isEmpty()) {
                return ResponseEntity.status(404).body("No ticket summary found.");
            }
            return ResponseEntity.ok(summaryList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
