package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.TicketReservationDTO;
import com.tauhoa.train.dtos.request.TicketReservationReqDTO;
import com.tauhoa.train.models.TicketReservation;
import com.tauhoa.train.services.TicketReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveTicket(@RequestBody @Valid TicketReservationReqDTO ticketReservationDTO){
        try{
            TicketReservation ticketReservation = ticketReservationService.save(ticketReservationDTO);
            return ResponseEntity.status(200).body(ticketReservation);
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
    @PostMapping("/deleteReserve")
    public ResponseEntity<?> deleteTicketReservation(@RequestBody @Valid TicketReservationReqDTO ticketReservationDTO){
        try {

            ticketReservationService.delete(ticketReservationDTO);
            return ResponseEntity.status(200).body("Complete");
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
