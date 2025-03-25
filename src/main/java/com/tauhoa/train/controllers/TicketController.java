package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.PassengerDTO;
import com.tauhoa.train.dtos.TicketDTO;
import com.tauhoa.train.dtos.TicketInformationDTO;
import com.tauhoa.train.models.Passenger;
import com.tauhoa.train.models.User;
import com.tauhoa.train.services.PassengerService;
import com.tauhoa.train.services.TicketService;
import com.tauhoa.train.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;
    private final PassengerService passengerService;
    @PostMapping("/confirmTicket")
    public ResponseEntity<String> bookTicket(@RequestBody @Valid TicketDTO request) {
        try {
            User user = userService.save(request.getUserDTO());
            for (TicketInformationDTO res : request.getTicketInformationDTO()) {
                PassengerDTO passengerDTO = new PassengerDTO();
                passengerDTO.setTicketType(res.getTicketType());
                passengerDTO.setCccd(res.getCccd());
                passengerDTO.setFullName(res.getFullName());
                Passenger passenger = passengerService.save(passengerDTO);
                ticketService.save(res, user, passenger);

            }

            return ResponseEntity.ok("Đặt vé thành công!");
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi đặt vé: " + e.getMessage() + request);
        }
    }
}
