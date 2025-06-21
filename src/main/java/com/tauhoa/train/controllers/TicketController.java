package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.request.*;
import com.tauhoa.train.dtos.response.*;

import com.tauhoa.train.models.*;
import com.tauhoa.train.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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
@GetMapping
public ResponseEntity<List<TicketResponseDTO>> getAllTickets() {
    List<Ticket> tickets = ticketService.getAllTickets();
    List<TicketResponseDTO> ticketDTOs = tickets.stream()
            .map(TicketResponseDTO::toTicketResponseDTO)
            .collect(Collectors.toList());

    if (ticketDTOs.isEmpty()) {
        return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(ticketDTOs);
}

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Ticket>> getTicketsByTripId(@PathVariable int tripId) {
        List<Ticket> tickets = ticketService.findAllByTripId(tripId);
        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build(); // or notFound()
        }
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/confirmTicket")
    public ResponseEntity<?> bookTicket(@RequestBody @Valid ReservationCodeRequestDTO request) {
        try {
            log.info("Yêu cầu xác nhận đặt vé: {}", request);
            if (request.getCustomerDTO() == null || request.getTicketRequestDTO() == null || request.getTicketRequestDTO().isEmpty()) {
                log.warn("Thông tin khách hàng hoặc vé bị thiếu.");
                return ResponseEntity.status(400).body("Vui lòng cung cấp đầy đủ thông tin khách hàng và vé.");
            }
            if( request.getCustomerDTO().getFullName() == null || request.getCustomerDTO().getEmail() == null ||
                    request.getCustomerDTO().getPhone() == null|| request.getCustomerDTO().getCccd() == null) {
                log.warn("Thông tin khách hàng không đầy đủ: {}", request.getCustomerDTO());
                return ResponseEntity.status(401).body("Vui lòng cung cấp đầy đủ thông tin khách hàng.");
            }
            if(!request.getCustomerDTO().getFullName().matches("^[\\p{L}\\s]+$")) {
                log.warn("Tên khách hàng không hợp lệ: {}", request.getCustomerDTO().getFullName());
                return ResponseEntity.status(402).body("Tên khách hàng không hợp lệ. Vui lòng nhập tên hợp lệ.");
            }
            if(!request.getCustomerDTO().getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                log.warn("Email khách hàng không hợp lệ: {}", request.getCustomerDTO().getEmail());
                return ResponseEntity.status(403).body("Email không hợp lệ. Vui lòng nhập email hợp lệ.");
            }
            if(!request.getCustomerDTO().getPhone().matches("^(0[3|5|7|8|9])+([0-9]{8})$")) {
                log.warn("Số điện thoại khách hàng không hợp lệ: {}", request.getCustomerDTO().getPhone());
                return ResponseEntity.status(406).body("Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại hợp lệ.");
            }
            if(!request.getCustomerDTO().getCccd().matches("^\\d{12}$")) {
                log.warn("CCCD khách hàng không hợp lệ: {}", request.getCustomerDTO().getCccd());
                return ResponseEntity.status(405).body("CCCD không hợp lệ. Vui lòng nhập CCCD hợp lệ.");
            }
            Customer customer = customerService.save(request.getCustomerDTO());
            log.info("Đã lưu khách hàng: {}", customer);
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (TicketRequestDTO res : request.getTicketRequestDTO()) {
                totalPrice = totalPrice.add(res.getTotalPrice());
            }
            ReservationCode reservationCode = reservationCodeService.save(totalPrice);
            log.info("Đã tạo mã đặt chỗ: {}", reservationCode);

            for (TicketRequestDTO res : request.getTicketRequestDTO()) {
                if(res.getFullName()==null || res.getCccd() == null || res.getTicketType() == null) {
                    log.warn("Thiếu thông hành khách: {}", res);
                    return ResponseEntity.status(400).body("Vui lòng cung cấp đầy đủ thông tin hành khách.");
                }
                if(!res.getFullName().matches("^[\\p{L}\\s]+$")) {
                    log.warn("Tên hành khách không hợp lệ: {}", res.getFullName());
                    return ResponseEntity.status(402).body("Tên hành khách không hợp lệ. Vui lòng nhập tên hợp lệ.");
                }
                if(!res.getCccd().matches("^\\d{12}$")) {
                    log.warn("CCCD hành khách không hợp lệ: {}", res.getCccd());
                    return ResponseEntity.status(405).body("CCCD hành khách không hợp lệ. Vui lòng nhập CCCD hợp lệ.");
                }
                PassengerDTO passengerDTO = new PassengerDTO();
                passengerDTO.setTicketType(res.getTicketType());
                passengerDTO.setCccd(res.getCccd());
                passengerDTO.setFullName(res.getFullName());
                Passenger passenger = passengerService.save(passengerDTO);
                log.info("Đã lưu hành khách: {}", passenger);
                ticketService.save(res, customer, passenger,reservationCode);
                log.info("Đã lưu vé cho hành khách: {}", passengerDTO.getFullName());
            }
            emailService.sendTicketEmail(customer,reservationCode.getReservationCodeId());
            BookingResponse response = new BookingResponse();
            response.setStatus("success");
            response.setMessage("Đặt vé thành công");
            return ResponseEntity.status(200).body(response);
        }catch (Exception e) {
            log.error("Lỗi khi đặt vé", e);
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
            log.info("Yêu cầu giữ vé: {}", ticketReservationDTO);
            if(ticketReservationDTO.getSeat() == null || ticketReservationDTO.getTrip() == null||
                    ticketReservationDTO.getDepartureStation() == null || ticketReservationDTO.getArrivalStation() == null) {
                log.warn("Thiếu thông tin giữ vé.");
                return ResponseEntity.status(500).body("Vui lòng cung cấp đầy đủ thông tin");
            }
            if(ticketReservationDTO.getSeat() < 1 || ticketReservationDTO.getTrip() <1 ){

                log.warn("Thông tin giữ vé không hợp lệ: {}", ticketReservationDTO);
                return ResponseEntity.status(500).body("Thông tin giữ vé không hợp lệ.");

            }
            boolean isValid = ticketService.validateTicketReservation(ticketReservationDTO);
            if(isValid){
                log.info("Vé đã được giữ trước đó: {}", ticketReservationDTO.getSeat());
                return ResponseEntity.status(400).body("Vé đã được giữ trước đó.");
            }
            TicketResponseDTO ticket = ticketService.saveReservation(ticketReservationDTO);
            log.info("Giữ vé thành công: {}", ticket);
            return ResponseEntity.status(200).body(ticket);
        } catch (Exception e){
            log.error("Lỗi khi giữ vé", e);
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
    @PostMapping("/deleteReserve")
    public ResponseEntity<?> deleteTicketReservation(@RequestBody @Valid TicketReservationReqDTO ticketReservationDTO){
        try {
            if(ticketReservationDTO.getSeat() == null || ticketReservationDTO.getTrip() == null||
                    ticketReservationDTO.getDepartureStation() == null || ticketReservationDTO.getArrivalStation() == null) {
                log.info("Yêu cầu huỷ giữ vé: {}", ticketReservationDTO);
                return ResponseEntity.status(500).body("Vui lòng cung cấp đầy đủ thông tin");
            }
            if(ticketReservationDTO.getSeat() < 1 || ticketReservationDTO.getTrip() <1 ){

                log.warn("Thông tin hủy giữ vé không hợp lệ: {}", ticketReservationDTO);
                return ResponseEntity.status(500).body("Thông tin hủy giữ vé không hợp lệ.");

            }
            ticketService.deleteTicket(ticketReservationDTO);

            // Trả về đối tượng BookingResponse thay vì chuỗi văn bản
            BookingResponse response = new BookingResponse();
            response.setStatus("success");
            response.setMessage("Hủy giữ vé thành công!");
            log.info("Huỷ giữ vé thành công cho seat {}", ticketReservationDTO.getSeat());
            return ResponseEntity.status(200).body(response);
        } catch (Exception e){
            log.error("Lỗi khi huỷ giữ vé", e);
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
            Ticket ticket = ticketService.findByTicketId(ticketId);
            SearchTicketResponse searchTicketResponse = new SearchTicketResponse(ticket.getTicketId(),
                    ticket.getPassenger().getFullname(),
                    ticket.getDepartureStation().getStationName(),
                    ticket.getArrivalStation().getStationName(),
                    ticket.getSeat().getSeatNumber(),
                    ticket.getTrip().getTrain().getTrainName());
            log.info("Đã tìm thấy vé : {}", searchTicketResponse);
            return ResponseEntity.ok(searchTicketResponse);
        } catch (RuntimeException e) {
            log.warn("Không tìm thấy vé với ID: {}", ticketId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy vé với ID: " + ticketId);
        }
    }

    @GetMapping("/searchTicketByReservationCodeAndroid")
    public ResponseEntity<?> searchTicketByReservationCodeAndroid(@RequestParam int reservationCode) {
        try {
            List<Ticket> tickets = ticketService.findByReservationCode(reservationCode);
            if (tickets.isEmpty()) {
                log.info("Không tìm thấy vé cho mã đặt chỗ: {}", reservationCode);
                return ResponseEntity.noContent().build();
            }
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
            log.info("Đã tìm thấy  {} vé cho mã đặt chỗ {}", tickets.size(), reservationCode);
            return ResponseEntity.ok(ticketResponseDTOList);
        } catch (RuntimeException e) {
            log.warn("Lỗi khi tìm kiếm vé với mã đặt chỗ: {}", reservationCode, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy vé với mã đặt vé: " + reservationCode);
        }
    }

    @PostMapping("/from-to")
    public ResponseEntity<?> getTicketsFromTo(@Valid @RequestBody TicketDateRangeRequestDTO request) {
        LocalDate start = LocalDate.parse(request.getStartDate());
        LocalDate end = LocalDate.parse(request.getEndDate());
        String type = request.getType();

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);

        if ("monthly".equalsIgnoreCase(type)) {
            List<MonthlySalesResponseDTO> sales = ticketService.getMonthlySales(startDateTime, endDateTime);
            return sales.isEmpty()
                    ? ResponseEntity.status(404).body("No monthly sales found.")
                    : ResponseEntity.ok(sales);
        }

        if ("daily".equalsIgnoreCase(type)) {
            List<DailySalesResponseDTO> dailySales = ticketService.getDailySales(startDateTime, endDateTime);
            return dailySales.isEmpty()
                    ? ResponseEntity.status(404).body("No daily sales found.")
                    : ResponseEntity.ok(dailySales);
        }

        List<TicketResponseDTO> tickets = ticketService.getTicketsBetween(startDateTime, endDateTime);

        return tickets.isEmpty()
                ? ResponseEntity.status(404).body("No tickets found.")
                : ResponseEntity.ok(tickets);
    }

    @GetMapping("/ticket-summary")
    public ResponseEntity<List<TicketCountResponseDTO>> getTicketSummary() {
        List<TicketCountResponseDTO> ticketSummary = ticketService.getTicketCountGroupedByStations();
        return ResponseEntity.ok(ticketSummary);
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        return ResponseEntity.ok(ticketService.getTotalRevenue());
    }
}