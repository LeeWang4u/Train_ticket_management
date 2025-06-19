package com.tauhoa.train.controllers;

import com.tauhoa.train.controllers.TicketController;
import com.tauhoa.train.dtos.response.SearchTicketResponse;
import com.tauhoa.train.models.*;
import com.tauhoa.train.services.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckTicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;
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
    private Ticket buildMockTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);

        Passenger passenger = new Passenger();
        passenger.setFullname("Nguyen Van A");
        ticket.setPassenger(passenger);

        Station departure = new Station();
        departure.setStationName("Hà Nội");
        ticket.setDepartureStation(departure);

        Station arrival = new Station();
        arrival.setStationName("Huế");
        ticket.setArrivalStation(arrival);

        Seat seat = new Seat();
        seat.setSeatNumber("A01");
        ticket.setSeat(seat);

        Train train = new Train();
        train.setTrainName("SE1");

        Trip trip = new Trip();
        trip.setTrain(train);
        ticket.setTrip(trip);

        return ticket;
    }

    @Test
    void searchTicketById_found_shouldReturnTicket() {
        Ticket mockTicket = buildMockTicket();
        when(ticketService.findByTicketId(1)).thenReturn(mockTicket);

        ResponseEntity<?> response = ticketController.searchTicketByIdAndroid(1);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof SearchTicketResponse);

        SearchTicketResponse body = (SearchTicketResponse) response.getBody();
        assertEquals("Nguyen Van A", body.getPassengerName());
        assertEquals("A01", body.getSeatName());
    }

    @Test
    void searchTicketById_notFound_shouldReturn404() {
        when(ticketService.findByTicketId(999)).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<?> response = ticketController.searchTicketByIdAndroid(999);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Không tìm thấy vé với ID: 999", response.getBody());
    }

    @Test
    void searchTicketByReservationCode_found_shouldReturnList() {
        Ticket mockTicket = buildMockTicket();
        List<Ticket> mockList = List.of(mockTicket);

        when(ticketService.findByReservationCode(1234)).thenReturn(mockList);

        ResponseEntity<?> response = ticketController.searchTicketByReservationCodeAndroid(1234);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof List<?>);

        List<?> list = (List<?>) response.getBody();
        assertFalse(list.isEmpty());
        assertTrue(list.get(0) instanceof SearchTicketResponse);

        SearchTicketResponse result = (SearchTicketResponse) list.get(0);
        assertEquals("SE1", result.getTrainName());
    }

    @Test
    void searchTicketByReservationCode_notFound_shouldReturn404() {
        when(ticketService.findByReservationCode(5678)).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<?> response = ticketController.searchTicketByReservationCodeAndroid(5678);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Không tìm thấy vé với mã đặt vé: 5678", response.getBody());
    }

    @Test
    void searchTicketByReservationCodeAndroid_emptyList_shouldReturn204() {
        when(ticketService.findByReservationCode(8888)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = ticketController.searchTicketByReservationCodeAndroid(8888);

        assertEquals(204, response.getStatusCodeValue());
    }
}
