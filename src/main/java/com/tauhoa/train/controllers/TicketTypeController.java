package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.response.TicketTypeCountResponseDTO;
import com.tauhoa.train.models.TicketType;
import com.tauhoa.train.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tickets")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    @GetMapping("/getTicketType")
    public ResponseEntity<?> getTicketType(){
        try{
            List<TicketType> ticketTypes = ticketTypeService.getAllTicketTypes();
            return ResponseEntity.status(200).body(ticketTypes);
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/count-by-type")
    public ResponseEntity<List<TicketTypeCountResponseDTO>> getTicketCountsByType() {
        List<TicketTypeCountResponseDTO> counts = ticketTypeService.getTicketCountsGroupedByType();
        return ResponseEntity.ok(counts);
    }
}
