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
//    @RegisterExtension
//    TestWatcher watcher = new TestWatcher() {
//        @Override
//        public void testSuccessful(ExtensionContext context) {
//            System.out.println("PASSED: " + context.getDisplayName());
//        }
//
//        @Override
//        public void testFailed(ExtensionContext context, Throwable cause) {
//            System.out.println("FAILED: " + context.getDisplayName());
//        }
//
//        @Override
//        public void testDisabled(ExtensionContext context, Optional<String> reason) {
//            System.out.println("DISABLED: " + context.getDisplayName() + " reason: " + reason.orElse("No reason"));
//        }
//    };
    private Ticket buildMockTicket(int ticketId ,String fullName,String departureStationName, String arrivalStationName, String seatNumber, String trainName) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);

        Passenger passenger = new Passenger();
        passenger.setFullname(fullName);
        ticket.setPassenger(passenger);

        Station departure = new Station();
        departure.setStationName(departureStationName);
        ticket.setDepartureStation(departure);

        Station arrival = new Station();
        arrival.setStationName(arrivalStationName);
        ticket.setArrivalStation(arrival);

        Seat seat = new Seat();
        seat.setSeatNumber(seatNumber);
        ticket.setSeat(seat);

        Train train = new Train();
        train.setTrainName(trainName);

        Trip trip = new Trip();
        trip.setTrain(train);
        ticket.setTrip(trip);

        return ticket;
    }


    void searchTicketById_found_shouldReturnTicket(int expectedStatus, int id,Ticket mockTicket) {
        when(ticketService.findByTicketId(id)).thenReturn(mockTicket);

        ResponseEntity<?> response = ticketController.searchTicketByIdAndroid(id);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof SearchTicketResponse);

        SearchTicketResponse body = (SearchTicketResponse) response.getBody();
        assertEquals(mockTicket.getPassenger().getFullname(), body.getPassengerName());
        assertEquals(mockTicket.getSeat().getSeatNumber(), body.getSeatName());
    }


    void searchTicketById_notFound_shouldReturn404(int expectedStatus, int id,Ticket mockTicket) {
        when(ticketService.findByTicketId(999)).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<?> response = ticketController.searchTicketByIdAndroid(999);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Không tìm thấy vé với ID: 999", response.getBody());
    }


    void searchTicketByReservationCode_found_shouldReturnList(int expectedStatus, int id,Ticket mockTicket) {
        List<Ticket> mockList = List.of(mockTicket);

        when(ticketService.findByReservationCode(id)).thenReturn(mockList);

        ResponseEntity<?> response = ticketController.searchTicketByReservationCodeAndroid(id);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof List<?>);

        List<?> list = (List<?>) response.getBody();
        assertFalse(list.isEmpty());
        assertTrue(list.get(0) instanceof SearchTicketResponse);

        SearchTicketResponse result = (SearchTicketResponse) list.get(0);
        assertEquals(mockTicket.getTicketId(), result.getTicketId());
    }


    void searchTicketByReservationCode_notFound_shouldReturn404(int expectedStatus, int id) {
        when(ticketService.findByReservationCode(id)).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<?> response = ticketController.searchTicketByReservationCodeAndroid(id);

        assertEquals(expectedStatus, response.getStatusCodeValue());
        assertEquals("Không tìm thấy vé với mã đặt vé: "+ id, response.getBody());
    }



    int TICKET_ID = 1;
    String PASSENGER_NAME = "NGUYEN VAN A";
    String FROM_STATION_NAME = "Hà Nội";
    String TO_STATION_NAME = "Huế";
    String SEAT_NUMBER = "A01";
    String TRAIN_NAME = "SE1";

    @Test
    void searchTicketById() {
        Ticket mockTicket = buildMockTicket(TICKET_ID, PASSENGER_NAME, FROM_STATION_NAME, TO_STATION_NAME, SEAT_NUMBER, TRAIN_NAME);
        searchTicketById_found_shouldReturnTicket(200, TICKET_ID, mockTicket);
        searchTicketById_notFound_shouldReturn404(404, 999, mockTicket);
    }
    @Test
    void searchTicketByReservationCode() {
        Ticket mockTicket = buildMockTicket(TICKET_ID, PASSENGER_NAME, FROM_STATION_NAME, TO_STATION_NAME, SEAT_NUMBER, TRAIN_NAME);
        searchTicketByReservationCode_found_shouldReturnList(200, TICKET_ID, mockTicket);
        searchTicketByReservationCode_notFound_shouldReturn404(404, 999);
    }

}
