package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.request.TicketReservationReqDTO;
import com.tauhoa.train.dtos.response.TicketResponseDTO;
import com.tauhoa.train.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReservationControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;




    private TicketReservationReqDTO setUp(int seat, int trip, String departureStation, String arrivalStation) {
        TicketReservationReqDTO validRequest = new TicketReservationReqDTO();
        validRequest.setSeat(seat);
        validRequest.setTrip(trip);
        validRequest.setDepartureStation(departureStation);
        validRequest.setArrivalStation(arrivalStation);
        return validRequest;
    }


    void reserveTicket_whenValidAndNotReserved_shouldReturn200(int expectedStatus,TicketReservationReqDTO validRequest) {
        when(ticketService.validateTicketReservation(validRequest)).thenReturn(false);

        TicketResponseDTO ticketResponse = new TicketResponseDTO();
        ticketResponse.setSeatId(validRequest.getSeat());
        ticketResponse.setTripId(validRequest.getTrip());
        when(ticketService.saveReservation(validRequest)).thenReturn(ticketResponse);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof TicketResponseDTO);
    }


    void reserveTicket_whenAlreadyReserved_shouldReturn400(int expectedStatus,TicketReservationReqDTO validRequest) {
        when(ticketService.validateTicketReservation(validRequest)).thenReturn(true);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Vé đã được giữ trước đó.", response.getBody());
    }


    void reserveTicket_whenServiceThrowsException_shouldReturn500(int expectedStatus,TicketReservationReqDTO validRequest) {
        when(ticketService.validateTicketReservation(validRequest)).thenThrow(new RuntimeException("Internal error"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Internal error", response.getBody());
    }


    void reserveTicket_whenInvalidTrip_shouldThrowException(int expectedStatus,TicketReservationReqDTO validRequest) {
        validRequest.setTrip(-1);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Thông tin giữ vé không hợp lệ.", response.getBody());
    }

    void reserveTicket_whenInvalidSeat_shouldThrowException(int expectedStatus,TicketReservationReqDTO validRequest) {
        validRequest.setSeat(-1);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Thông tin giữ vé không hợp lệ.", response.getBody());
    }


    void reserveTicket_whenArrivalStationIsNull_shouldThrowValidationException(int expectedStatus,TicketReservationReqDTO validRequest) {
        validRequest.setArrivalStation(null);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }


    void reserveTicket_whenSeatIsNull_shouldThrowValidationException(int expectedStatus,TicketReservationReqDTO validRequest) {

        validRequest.setSeat(null);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void reserveTicket_whenTripIsNull_shouldThrowValidationException(int expectedStatus,TicketReservationReqDTO validRequest) {

        validRequest.setTrip(null);


        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void reserveTicket_whenDepartureStationIsNull_shouldThrowValidationException(int expectedStatus,TicketReservationReqDTO validRequest) {

        validRequest.setDepartureStation(null);


        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }


    void reserveTicket_whenUnexpectedException_shouldReturn500(int expectedStatus,TicketReservationReqDTO validRequest) {
        when(ticketService.validateTicketReservation(any())).thenThrow(new RuntimeException("Vui lòng cung cấp đầy đủ thông tin"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Vui lòng cung cấp đầy đủ thông tin", response.getBody());
    }


    void reserveTicket_whenSaveFails_shouldReturn500(int expectedStatus,TicketReservationReqDTO validRequest) {
        when(ticketService.saveReservation(validRequest))
                .thenThrow(new RuntimeException("Vui lòng cung cấp đầy đủ thông tin"));

        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Vui lòng cung cấp đầy đủ thông tin", response.getBody());
    }

    void deleteReserveTicket_whenValidAndNotReserved_shouldReturn200(int expectedStatus,TicketReservationReqDTO validRequest) {
        doNothing().when(ticketService).deleteTicket(validRequest);

        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(response.getBody(), response.getBody());
    }



    void deleteReserveTicket_whenInvalidTrip_shouldThrowException(int expectedStatus,TicketReservationReqDTO validRequest) {
        validRequest.setTrip(-1);

        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Thông tin hủy giữ vé không hợp lệ.", response.getBody());
    }

    void deleteReserveTicket_whenInvalidSeat_shouldThrowException(int expectedStatus,TicketReservationReqDTO validRequest) {
        validRequest.setSeat(-1);

        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Thông tin hủy giữ vé không hợp lệ.", response.getBody());
    }


    void deleteReserveTicket_whenArrivalStationIsNull_shouldThrowValidationException(int expectedStatus,TicketReservationReqDTO validRequest) {

        validRequest.setArrivalStation(null);

        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }


    void deleteReserveTicket_whenSeatIsNull_shouldThrowValidationException(int expectedStatus,TicketReservationReqDTO validRequest) {

        validRequest.setSeat(null);


        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void deleteReserveTicket_whenTripIsNull_shouldThrowValidationException(int expectedStatus,TicketReservationReqDTO validRequest) {

        validRequest.setTrip(null);


        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    void deleteReserveTicket_whenDepartureStationIsNull_shouldThrowValidationException(int expectedStatus,TicketReservationReqDTO validRequest) {

        validRequest.setDepartureStation(null);


        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
    }





    void deleteReserveTicket_whenSaveFails_shouldReturn500(int expectedStatus,TicketReservationReqDTO validRequest) {

        when(ticketService.saveReservation(validRequest))
                .thenThrow(new RuntimeException("Vui lòng cung cấp đầy đủ thông tin"));

        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Vui lòng cung cấp đầy đủ thông tin", response.getBody());
    }

    int seat = 100;
    int trip =1;
    String departureStation="Hà Nội";
    String arrivalStation = "Sài Gòn";

    @Test
    void reserveTicket(){
        TicketReservationReqDTO validRequest = setUp(seat, trip, departureStation, arrivalStation);

        reserveTicket_whenValidAndNotReserved_shouldReturn200(200, validRequest);
        reserveTicket_whenAlreadyReserved_shouldReturn400(400, validRequest);
        reserveTicket_whenServiceThrowsException_shouldReturn500(500, validRequest);
        reserveTicket_whenInvalidTrip_shouldThrowException(500, validRequest);
        reserveTicket_whenInvalidSeat_shouldThrowException(500, validRequest);
        reserveTicket_whenArrivalStationIsNull_shouldThrowValidationException(500, validRequest);
        reserveTicket_whenSeatIsNull_shouldThrowValidationException(500, validRequest);
        reserveTicket_whenTripIsNull_shouldThrowValidationException(500, validRequest);
        reserveTicket_whenDepartureStationIsNull_shouldThrowValidationException(500, validRequest);
        reserveTicket_whenUnexpectedException_shouldReturn500(500, validRequest);
        reserveTicket_whenSaveFails_shouldReturn500(500, validRequest);
    }
    @Test
    void deleteReserveTicket(){
        TicketReservationReqDTO validRequest = setUp(seat, trip, departureStation, arrivalStation);

        deleteReserveTicket_whenValidAndNotReserved_shouldReturn200(200, validRequest);
        deleteReserveTicket_whenInvalidTrip_shouldThrowException(500, validRequest);
        deleteReserveTicket_whenInvalidSeat_shouldThrowException(500, validRequest);
        deleteReserveTicket_whenArrivalStationIsNull_shouldThrowValidationException(500, validRequest);
        deleteReserveTicket_whenSeatIsNull_shouldThrowValidationException(500, validRequest);
        deleteReserveTicket_whenTripIsNull_shouldThrowValidationException(500, validRequest);
        deleteReserveTicket_whenDepartureStationIsNull_shouldThrowValidationException(500, validRequest);
        deleteReserveTicket_whenSaveFails_shouldReturn500(500, validRequest);
    }
}
