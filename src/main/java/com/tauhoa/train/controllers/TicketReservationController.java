package com.tauhoa.train.controllers;

import com.tauhoa.train.models.TicketReservation;
import com.tauhoa.train.services.TicketReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ticketReservation")
public class TicketReservationController {
    private final TicketReservationService ticketReservationService;
    @GetMapping("/getReservation")
    public ResponseEntity<?> getReservation(@RequestParam int id){
        try{
            System.out.println("API được gọi với ID: " + id);
            TicketReservation ticketReservation =  ticketReservationService.getTicketReservation(id);
            return ResponseEntity.status(200).body(ticketReservation);
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
