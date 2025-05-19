package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.request.PassengerDTO;
import com.tauhoa.train.dtos.request.ReservationCodeRequestDTO;
import com.tauhoa.train.dtos.request.TicketRequestDTO;
import com.tauhoa.train.dtos.request.TicketReservationReqDTO;
import com.tauhoa.train.dtos.request.TicketSearchRequestDTO;
import com.tauhoa.train.dtos.response.BookingResponse;
import com.tauhoa.train.dtos.response.SearchTicketResponse;
import com.tauhoa.train.dtos.response.TicketResponseDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.TicketRepository;
import com.tauhoa.train.repositories.TrainScheduleRepository;
import com.tauhoa.train.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final ReservationCodeService reservationCodeService;
    private final PassengerService passengerService;
    private final CustomerService customerService;
    private final EmailService emailService;
//    private final TicketRepository ticketRepository;
//    private final TrainScheduleRepository trainScheduleRepository;
//    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @PostMapping("/confirmTicket")
    public ResponseEntity<?> bookTicket(@RequestBody @Valid ReservationCodeRequestDTO request) {
        try {
            Customer customer = customerService.save(request.getCustomerDTO());
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (TicketRequestDTO res : request.getTicketRequestDTO()) {
                totalPrice = totalPrice.add(res.getTotalPrice());
            }
            ReservationCode reservationCode = reservationCodeService.save(totalPrice);
            System.out.println(reservationCode);
            for (TicketRequestDTO res : request.getTicketRequestDTO()) {
                PassengerDTO passengerDTO = new PassengerDTO();
                passengerDTO.setTicketType(res.getTicketType());
                passengerDTO.setCccd(res.getCccd());
                passengerDTO.setFullName(res.getFullName());
                Passenger passenger = passengerService.save(passengerDTO);
                ticketService.save(res, customer, passenger,reservationCode);
            }
            emailService.sendTicketEmail(customer,reservationCode.getReservationCodeId());
            BookingResponse response = new BookingResponse();
            response.setStatus("success");
            response.setMessage("Đặt vé thành công");
            return ResponseEntity.status(200).body(response);
        }catch (Exception e) {
            BookingResponse response = new BookingResponse();
            response.setStatus("error");
            response.setMessage("Đặt vé thất bại!");
            return ResponseEntity.status(500).body(response);
        }
    }

//    @GetMapping("/testTicket")
//    public ResponseEntity<?> testTicket(@RequestParam int request) {
//        try{
//            Ticket ticket = ticketRepository.findByTicketId(request);
//            Optional<TrainSchedule> departureTime = trainScheduleRepository.findByTrainIdAndStationId(ticket.getTrip().getTrain().getTrainId(), ticket.getDepartureStation().getStationId());
//            Optional<TrainSchedule> arrivalTime = trainScheduleRepository.findByTrainIdAndStationId(ticket.getTrip().getTripId(), ticket.getArrivalStation().getStationId());
//            String departureTimeString = departureTime.map(TrainSchedule::getDepartureTime).map(time -> time.format(timeFormatter))
//                    .orElse("N/A");
//                return ResponseEntity.status(200).body(departureTimeString);
//        } catch (Exception e){
//                return ResponseEntity.status(500).body(e.getMessage());
//        }
//    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveTicket(@RequestBody @Valid TicketReservationReqDTO ticketReservationDTO){
        try{
            TicketResponseDTO ticket = ticketService.saveReservation(ticketReservationDTO);
            return ResponseEntity.status(200).body(ticket);
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
    @PostMapping("/deleteReserve")
    public ResponseEntity<?> deleteTicketReservation(@RequestBody @Valid TicketReservationReqDTO ticketReservationDTO){
        try {
            ticketService.deleteTicket(ticketReservationDTO);

            // Trả về đối tượng BookingResponse thay vì chuỗi văn bản
            BookingResponse response = new BookingResponse();
            response.setStatus("success");
            response.setMessage("Hủy giữ vé thành công!");
            return ResponseEntity.status(200).body(response);
        } catch (Exception e){
            // Trả về đối tượng BookingResponse với thông báo lỗi
            BookingResponse errorResponse = new BookingResponse();
            errorResponse.setStatus("error");
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
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


    @PostMapping("/searchByReservationCode")
    public ResponseEntity<?> searchTicketByReservationCode(@RequestBody Map<String, Integer> request) {
        Integer reservationCode = request.get("reservationCode");

        if (reservationCode == null) {
            return ResponseEntity.badRequest().body("Vui lòng cung cấp mã đặt vé!");
        }

        try {
            List<Ticket> tickets = ticketService.findByReservationCode(reservationCode);
            if (tickets.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(tickets);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy vé với mã đặt vé: " + reservationCode);
        }
    }

    @GetMapping("/searchTicketByIdAndroid")
    public ResponseEntity<?> searchTicketByIdAndroid(@RequestParam int ticketId) {
        try {
            System.out.println(ticketId);
            Ticket ticket = ticketService.findByTicketId(ticketId);
            SearchTicketResponse searchTicketResponse = new SearchTicketResponse(ticket.getTicketId(),
                    ticket.getPassenger().getFullname(),
                    ticket.getDepartureStation().getStationName(),
                    ticket.getArrivalStation().getStationName(),
                    ticket.getSeat().getSeatNumber(),
                    ticket.getTrip().getTrain().getTrainName());
            return ResponseEntity.ok(searchTicketResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy vé với ID: " + ticketId);
        }
    }

    @GetMapping("/searchTicketByReservationCodeAndroid")
    public ResponseEntity<?> searchTicketByReservationCodeAndroid(@RequestParam int reservationCode) {
        try {
            List<Ticket> tickets = ticketService.findByReservationCode(reservationCode);
            List<SearchTicketResponse> ticketResponseDTOList = new ArrayList<>();
            for (Ticket ticket : tickets) {
                SearchTicketResponse searchTicketResponse = new SearchTicketResponse(ticket.getTicketId(),
                        ticket.getPassenger().getFullname(),
                        ticket.getDepartureStation().getStationName(),
                        ticket.getArrivalStation().getStationName(),
                        ticket.getSeat().getSeatNumber(),
                        ticket.getTrip().getTrain().getTrainName());
                ticketResponseDTOList.add(searchTicketResponse);
            }
            if (tickets.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(ticketResponseDTOList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy vé với mã đặt vé: " + reservationCode);
        }
    }

}