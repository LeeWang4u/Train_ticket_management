package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.PassengerDTO;
import com.tauhoa.train.dtos.TicketDTO;
import com.tauhoa.train.dtos.TicketInformationDTO;
import com.tauhoa.train.dtos.request.TicketSearchRequestDTO;
import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.ReservationCode;
import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.models.Ticket;
import com.tauhoa.train.services.InvoiceService;
import com.tauhoa.train.services.PassengerService;
import com.tauhoa.train.services.TicketService;
import com.tauhoa.train.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
   // private final InvoiceService invoiceService;
    private final PassengerService passengerService;
    private final CustomerService customerService;

    @PostMapping("/confirmTicket")
    public ResponseEntity<String> bookTicket(@RequestBody @Valid TicketDTO request) {
        try {
            Customer customer = customerService.save(request.getCustomerDTO());
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (TicketInformationDTO res : request.getTicketInformationDTO()) {
                totalPrice = totalPrice.add(res.getTotalPrice());
            }
            //ReservationCode invoice = invoiceService.save(totalPrice);
            for (TicketInformationDTO res : request.getTicketInformationDTO()) {
                PassengerDTO passengerDTO = new PassengerDTO();
                passengerDTO.setTicketType(res.getTicketType());
                passengerDTO.setCccd(res.getCccd());
                passengerDTO.setFullName(res.getFullName());
                Passenger passenger = passengerService.save(passengerDTO);
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

    @PostMapping("/searchTicket")
    public ResponseEntity<?> searchTicketById(@RequestBody TicketSearchRequestDTO request) {
        Integer ticketId = request.getTicketId();
        if (ticketId == null) {
            return ResponseEntity.badRequest().body("Vui lòng cung cấp ticketId!");
        }

        try {
            Ticket ticket = ticketService.findByTicketId(ticketId);
            return ResponseEntity.ok(ticket); // Trả trực tiếp entity
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy vé với ID: " + ticketId);
        }
    }

    @PostMapping("/searchCusTicket")
    public ResponseEntity<?> searchTicketsByCustomer(@RequestBody TicketSearchRequestDTO request) {
        String cccd = request.getCccd();
        String phone = request.getPhone();

        if (cccd == null || cccd.isEmpty() || phone == null || phone.isEmpty()) {
            return ResponseEntity.badRequest().body("Vui lòng nhập đầy đủ cả CCCD và số điện thoại!");
        }

        List<Ticket> tickets = ticketService.findByCustomer(cccd, phone);
        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tickets);
    }
}