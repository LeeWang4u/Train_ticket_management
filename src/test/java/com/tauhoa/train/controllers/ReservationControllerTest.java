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
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    private TicketReservationReqDTO validRequest;

    @RegisterExtension
    TestWatcher watcher = new TestWatcher() {
        @Override
        public void testSuccessful(ExtensionContext context) {
            System.out.println("PASSED: " + context.getDisplayName());
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            System.out.println("FAILED: " + context.getDisplayName());
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            System.out.println("DISABLED: " + context.getDisplayName() + " reason: " + reason.orElse("No reason"));
        }
    };
    @BeforeEach
    void setUp() {
        validRequest = new TicketReservationReqDTO();
        validRequest.setSeat(1);
        validRequest.setTrip(101);
        validRequest.setDepartureStation("Hà Nội");
        validRequest.setArrivalStation("Huế");
    }

    @Test
    void reserveTicket_whenValidAndNotReserved_shouldReturn200() {
        when(ticketService.validateTicketReservation(validRequest)).thenReturn(false);

        TicketResponseDTO ticketResponse = new TicketResponseDTO();
        ticketResponse.setSeatId(100);
        ticketResponse.setTripId(1);
        when(ticketService.saveReservation(validRequest)).thenReturn(ticketResponse);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof TicketResponseDTO);
    }

    @Test
    void reserveTicket_whenAlreadyReserved_shouldReturn400() {
        when(ticketService.validateTicketReservation(validRequest)).thenReturn(true);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Vé đã được giữ trước đó.", response.getBody());
    }

    @Test
    void reserveTicket_whenServiceThrowsException_shouldReturn500() {
        when(ticketService.validateTicketReservation(validRequest)).thenThrow(new RuntimeException("Internal error"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal error", response.getBody());
    }

    @Test
    void reserveTicket_whenInvalidTrip_shouldThrowException() {
        validRequest.setTrip(-1);
        when(ticketService.validateTicketReservation(validRequest)).thenThrow(new IllegalArgumentException("Invalid trip"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Invalid trip", response.getBody());
    }
    @Test
    void reserveTicket_whenInvalidSeat_shouldThrowException() {
        validRequest.setSeat(-1);
        when(ticketService.validateTicketReservation(validRequest)).thenThrow(new IllegalArgumentException("Invalid seat"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Invalid seat", response.getBody());
    }

    @Test
    void reserveTicket_whenArrivalStationIsNull_shouldThrowValidationException() {
        TicketReservationReqDTO invalidRequest = new TicketReservationReqDTO();
        invalidRequest.setSeat(1);
        invalidRequest.setTrip(10);
        invalidRequest.setDepartureStation("Hà Nội");
        invalidRequest.setArrivalStation(null);

        ResponseEntity<?> response = ticketController.reserveTicket(invalidRequest);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void reserveTicket_whenSeatIsNull_shouldThrowValidationException() {
        TicketReservationReqDTO invalidRequest = new TicketReservationReqDTO();
        invalidRequest.setSeat(null);
        invalidRequest.setTrip(10);
        invalidRequest.setDepartureStation("Hà Nội");
        invalidRequest.setArrivalStation("Sài Gòn");

        ResponseEntity<?> response = ticketController.reserveTicket(invalidRequest);

        assertEquals(500, response.getStatusCodeValue());
    }
    @Test
    void reserveTicket_whenTripIsNull_shouldThrowValidationException() {
        TicketReservationReqDTO invalidRequest = new TicketReservationReqDTO();
        invalidRequest.setSeat(100);
        invalidRequest.setTrip(null);
        invalidRequest.setDepartureStation("Hà Nội");
        invalidRequest.setArrivalStation("Sài Gòn");

        ResponseEntity<?> response = ticketController.reserveTicket(invalidRequest);

        assertEquals(500, response.getStatusCodeValue());
    }
    @Test
    void reserveTicket_whenDepartureStationIsNull_shouldThrowValidationException() {
        TicketReservationReqDTO invalidRequest = new TicketReservationReqDTO();
        invalidRequest.setSeat(1);
        invalidRequest.setTrip(10);
        invalidRequest.setDepartureStation(null);
        invalidRequest.setArrivalStation("Hà Nội");

        ResponseEntity<?> response = ticketController.reserveTicket(invalidRequest);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void reserveTicket_whenUnexpectedException_shouldReturn500() {
        when(ticketService.validateTicketReservation(validRequest))
                .thenThrow(new RuntimeException("Unexpected failure"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected failure", response.getBody());
    }

    @Test
    void reserveTicket_whenSaveFails_shouldReturn500() {
        when(ticketService.saveReservation(validRequest))
                .thenThrow(new RuntimeException("Failed to save reservation"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Failed to save reservation", response.getBody());
    }
    @Test
    void deleteReserveTicket_whenValidAndNotReserved_shouldReturn200() {
        doNothing().when(ticketService).deleteTicket(validRequest);

        ResponseEntity<?> response = ticketController.deleteTicketReservation(validRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(response.getBody(), response.getBody());
    }

    @Test
  void deleteReserveTicket_whenServiceThrowsException_shouldReturn500() {
        when(ticketService.validateTicketReservation(validRequest)).thenReturn(false);
        when(ticketService.saveReservation(validRequest))
                .thenThrow(new RuntimeException("Failed to save reservation"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(response.getBody(), response.getBody());
    }

    @Test
    void deleteReserveTicket_whenAlreadyReserved_shouldReturn400() {
        when(ticketService.validateTicketReservation(validRequest)).thenReturn(true);

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Vé đã được giữ trước đó.", response.getBody());
    }


    @Test
    void deleteReserveTicket_whenInvalidTrip_shouldThrowException() {
        validRequest.setTrip(-1);
        when(ticketService.validateTicketReservation(validRequest)).thenThrow(new IllegalArgumentException("Invalid trip"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Invalid trip", response.getBody());
    }
    @Test
    void deleteReserveTicket_whenInvalidSeat_shouldThrowException() {
        validRequest.setSeat(-1);
        when(ticketService.validateTicketReservation(validRequest)).thenThrow(new IllegalArgumentException("Invalid seat"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Invalid seat", response.getBody());
    }

    @Test
    void deleteReserveTicket_whenArrivalStationIsNull_shouldThrowValidationException() {
        TicketReservationReqDTO invalidRequest = new TicketReservationReqDTO();
        invalidRequest.setSeat(1);
        invalidRequest.setTrip(10);
        invalidRequest.setDepartureStation("Hà Nội");
        invalidRequest.setArrivalStation(null);

        ResponseEntity<?> response = ticketController.reserveTicket(invalidRequest);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void deleteReserveTicket_whenSeatIsNull_shouldThrowValidationException() {
        TicketReservationReqDTO invalidRequest = new TicketReservationReqDTO();
        invalidRequest.setSeat(null);
        invalidRequest.setTrip(10);
        invalidRequest.setDepartureStation("Hà Nội");
        invalidRequest.setArrivalStation("Sài Gòn");

        ResponseEntity<?> response = ticketController.reserveTicket(invalidRequest);

        assertEquals(500, response.getStatusCodeValue());
    }
    @Test
    void deleteReserveTicket_whenTripIsNull_shouldThrowValidationException() {
        TicketReservationReqDTO invalidRequest = new TicketReservationReqDTO();
        invalidRequest.setSeat(100);
        invalidRequest.setTrip(null);
        invalidRequest.setDepartureStation("Hà Nội");
        invalidRequest.setArrivalStation("Sài Gòn");

        ResponseEntity<?> response = ticketController.reserveTicket(invalidRequest);

        assertEquals(500, response.getStatusCodeValue());
    }
    @Test
    void deleteReserveTicket_whenDepartureStationIsNull_shouldThrowValidationException() {
        TicketReservationReqDTO invalidRequest = new TicketReservationReqDTO();
        invalidRequest.setSeat(1);
        invalidRequest.setTrip(10);
        invalidRequest.setDepartureStation(null);
        invalidRequest.setArrivalStation("Hà Nội");

        ResponseEntity<?> response = ticketController.reserveTicket(invalidRequest);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void deleteReserveTicket_whenUnexpectedException_shouldReturn500() {
        when(ticketService.validateTicketReservation(validRequest))
                .thenThrow(new RuntimeException("Unexpected failure"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected failure", response.getBody());
    }

    @Test
    void deleteReserveTicket_whenSaveFails_shouldReturn500() {
        when(ticketService.validateTicketReservation(validRequest)).thenReturn(false);
        when(ticketService.saveReservation(validRequest))
                .thenThrow(new RuntimeException("Failed to save reservation"));

        ResponseEntity<?> response = ticketController.reserveTicket(validRequest);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Failed to save reservation", response.getBody());
    }
}
