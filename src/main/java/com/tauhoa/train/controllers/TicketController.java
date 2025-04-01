package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.PassengerDTO;
import com.tauhoa.train.dtos.TicketDTO;
import com.tauhoa.train.dtos.TicketInformationDTO;
import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.Invoice;
import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final InvoiceService invoiceService;
    private final PassengerService passengerService;
    private final CustomerService customerService;
    private final TicketReservationService ticketReservationService;
    @PostMapping("/confirmTicket")
    public ResponseEntity<String> bookTicket(@RequestBody @Valid TicketDTO request) {
        try {
            System.out.println(request);
            Customer customer = customerService.save(request.getCustomerDTO());
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (TicketInformationDTO res : request.getTicketInformationDTO()) {
                totalPrice = totalPrice.add(res.getTotalPrice());
            }
            Invoice invoice = invoiceService.save(totalPrice);
            for (TicketInformationDTO res : request.getTicketInformationDTO()) {
                PassengerDTO passengerDTO = new PassengerDTO();
                passengerDTO.setTicketType(res.getTicketType());
                passengerDTO.setCccd(res.getCccd());
                passengerDTO.setFullName(res.getFullName());
                Passenger passenger = passengerService.save(passengerDTO);
                ticketReservationService.update(res.getTicketReservation());
                ticketService.save(res, customer, passenger,invoice);
            }

            return ResponseEntity.status(200).body("Đặt vé thành công!");
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi đặt vé: " + e.getMessage() + request);
        }
    }

    @PostMapping("/testTicket")
    public ResponseEntity<String> testTicket(@RequestBody String request) {
        try{
                System.out.println( request+"API được gọi được: " );
                return ResponseEntity.status(200).body("Success");
        } catch (Exception e){
                return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}