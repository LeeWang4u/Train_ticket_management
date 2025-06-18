package com.tauhoa.train.controllers;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.StationRepository;
import com.tauhoa.train.repositories.TrainScheduleRepository;
import com.tauhoa.train.repositories.TripRepository;
import com.tauhoa.train.services.SeatService;
import com.tauhoa.train.services.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TripControllerTest {

    @Mock
    private TripService tripService;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private TrainScheduleRepository trainScheduleRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private TripController tripController; // Replace with your actual controller class name

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tripController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    // TC01: departure, arrival, date = null
    @Test
    void testSearchTripsWithNullParameters() throws Exception {
//        TripSearchRequestDTO request = new TripSearchRequestDTO();
//        request.setDepartureStation(null);
//        request.setArrivalStation(null);
////        request.setTripDate(null); LocalDate.of(2025, 6, 15)
//        request.setTripDate(LocalDate.of(2025, 6, 15));
        TripSearchRequestDTO request = new TripSearchRequestDTO(null, null, LocalDate.of(2025, 4, 10));

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid parameters: departureStation, arrivalStation, and tripDate are required"));
    }

    // TC02: departure = arrival
    @Test
    void testSearchTripsWithSameStations() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Hanoi");
        request.setTripDate(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Departure and arrival stations cannot be the same"));
    }

    // TC03: tripDate < today
    @Test
    void testSearchTripsWithPastDate() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().minusDays(1));

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Trip date cannot be in the past"));
    }

    // TC04: departure station not found in DB
    @Test
    void testSearchTripsWithNonExistentDepartureStation() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("NonExistentStation");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().plusDays(1));

        when(stationRepository.findByStationName("NonExistentStation"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Departure station not found: NonExistentStation"));
    }

    // TC05: arrival station not found in DB
    @Test
    void testSearchTripsWithNonExistentArrivalStation() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("NonExistentStation");
        request.setTripDate(LocalDate.now().plusDays(1));

        when(stationRepository.findByStationName("Hanoi"))
                .thenReturn(Optional.of(new Station(1, "Hanoi", "Hà Nội")));
        when(stationRepository.findByStationName("NonExistentStation"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Arrival station not found: NonExistentStation"));
    }

    // TC06: valid stations and date, but no trips found
    @Test
    void testSearchTripsWithNoMatchingTrips() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().plusDays(1));

        when(stationRepository.findByStationName("Hanoi"))
                .thenReturn(Optional.of(new Station(1, "Hanoi", "Hà Nội")));
        when(stationRepository.findByStationName("Saigon"))
                .thenReturn(Optional.of(new Station(2, "Saigon", "Hà Nội")));
        when(tripService.findTripsByStationsAndDate(any(TripSearchRequestDTO.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("No trips found for the given stations and date"));
    }

    // TC07: Happy path - at least one trip found
    @Test
    void testSearchTripsWithValidTrip() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().plusDays(1));

        Station departureStation = new Station(1, "Hanoi" , "Hà Nội");
        Station arrivalStation = new Station(2, "Saigon", "Hồ Chí Minh");

        when(stationRepository.findByStationName("Hanoi")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Saigon")).thenReturn(Optional.of(arrivalStation));

        BigDecimal x = new BigDecimal("100.5");
        BigDecimal y = new BigDecimal("200.5");

        Route route = new Route(1, "Hanoi - Saigon");

        Train train = new Train(1, "Train1", route);
        Trip trip = new Trip();
        trip.setTripId(1001);
        trip.setTrain(train);
        trip.setBasePrice(BigDecimal.valueOf(500000));
        trip.setTripDate(LocalDate.of(2025, 7, 15));
        trip.setTripStatus("Scheduled");

        TrainSchedule departureSchedule = new TrainSchedule( departureStation,train,
                LocalTime.of(8, 0), LocalTime.of(0, 0), 1, x, 1);
        TrainSchedule arrivalSchedule = new TrainSchedule(arrivalStation,train,
                LocalTime.of(10, 0), LocalTime.of(0, 0), 2, y, 1);


        when(stationRepository.findByStationName("Hanoi")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Saigon")).thenReturn(Optional.of(arrivalStation));
        when(tripRepository.findAllWithStatus()).thenReturn(Arrays.asList(trip));
        when(trainScheduleRepository.findByTrainId(1)).thenReturn(Arrays.asList(departureSchedule, arrivalSchedule));
        when(seatService.getNumberEmptySeats(1, "Hanoi", "Saigon")).thenReturn(10);

        TripResponseDTO responseDTO = new TripResponseDTO(
                1, "Train1", BigDecimal.valueOf(500), LocalDate.now(), "Scheduled",
                "Hanoi", "Saigon",
                LocalDate.now().plusDays(1).atTime(8, 0),
                LocalDate.now().plusDays(1).atTime(12, 0),
                10
        );
        when(tripService.findTripsByStationsAndDate(any(TripSearchRequestDTO.class)))
                .thenReturn(Arrays.asList(responseDTO));

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tripId").value(1))
                .andExpect(jsonPath("$[0].trainName").value("Train1"))
                .andExpect(jsonPath("$[0].availableSeats").value(10));
    }

    // TC08: Trip exists but no schedules
    @Test
    void testSearchTripsWithNoSchedules() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().plusDays(1));

        Station departureStation = new Station(1, "Hanoi", "Hà Nội");
        Station arrivalStation = new Station(2, "Saigon", "Hồ Chí Minh");
        Route route = new Route(1, "Hanoi - Saigon");

        BigDecimal x = new BigDecimal("100.5");
        BigDecimal y = new BigDecimal("200.5");

        Train train = new Train(1, "Train1", route);
        Trip trip = new Trip();
        trip.setTripId(1001);
        trip.setTrain(train);
        trip.setBasePrice(BigDecimal.valueOf(500000));
        trip.setTripDate(LocalDate.of(2025, 7, 15));
        trip.setTripStatus("Scheduled");

        when(stationRepository.findByStationName("Hanoi")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Saigon")).thenReturn(Optional.of(arrivalStation));
        when(tripRepository.findAllWithStatus()).thenReturn(Arrays.asList(trip));
        when(trainScheduleRepository.findByTrainId(1)).thenReturn(Collections.emptyList());
        when(tripService.findTripsByStationsAndDate(any(TripSearchRequestDTO.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("No trips found for the given stations and date"));
    }

    // TC09: Trip exists, has schedules, but missing departure or arrival station
    @Test
    void testSearchTripsWithMissingStationSchedule() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().plusDays(1));

        Station departureStation = new Station(1, "Hanoi", "Hà Nội");
        Station otherStation = new Station(2, "Saigon", "Hồ Chí Minh");
        Route route = new Route(1, "Hanoi - Saigon");

        BigDecimal x = new BigDecimal("100.5");
        BigDecimal y = new BigDecimal("200.5");

        Train train = new Train(1, "Train1", route);
        Trip trip = new Trip();
        trip.setTripId(1001);
        trip.setTrain(train);
        trip.setBasePrice(BigDecimal.valueOf(500000));
        trip.setTripDate(LocalDate.of(2025, 7, 15));
        trip.setTripStatus("Scheduled");

        // Trip trip = new Trip(1, train, LocalDate.now(), 100.0, "ACTIVE");
        TrainSchedule otherSchedule = new TrainSchedule( departureStation,train,
                LocalTime.of(8, 0), LocalTime.of(0, 0), 1, x, 1);

        when(stationRepository.findByStationName("Hanoi")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Saigon")).thenReturn(Optional.of(new Station(2, "Saigon", "Hồ Chí Minh")));
        when(tripRepository.findAllWithStatus()).thenReturn(Arrays.asList(trip));
        when(trainScheduleRepository.findByTrainId(1)).thenReturn(Arrays.asList(otherSchedule));
        when(tripService.findTripsByStationsAndDate(any(TripSearchRequestDTO.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("No trips found for the given stations and date"));
    }

    // TC10: Trip exists, has schedules, but ordinal numbers are incorrect
    @Test
    void testSearchTripsWithInvalidOrdinal() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().plusDays(1));

        Station departureStation = new Station(1, "Hanoi", "Hà Nội");
        Station arrivalStation = new Station(2, "Saigon", "Hồ Chí Minh");
        Route route = new Route(1, "Hanoi - Saigon");

        BigDecimal x = new BigDecimal("100.5");
        BigDecimal y = new BigDecimal("200.5");

        Train train = new Train(1, "Train1", route);
        Trip trip = new Trip();
        trip.setTripId(1001);
        trip.setTrain(train);
        trip.setBasePrice(BigDecimal.valueOf(500000));
        trip.setTripDate(LocalDate.of(2025, 7, 15));
        trip.setTripStatus("Scheduled");

        // Trip trip = new Trip(1, train, LocalDate.now(), 100.0, "ACTIVE");
        TrainSchedule departureSchedule = new TrainSchedule( departureStation,train,
                LocalTime.of(8, 0), LocalTime.of(0, 0), 1, x, 1);
        TrainSchedule arrivalSchedule = new TrainSchedule(arrivalStation,train,
                LocalTime.of(10, 0), LocalTime.of(0, 0), 2, y, 1);


        when(stationRepository.findByStationName("Hanoi")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Saigon")).thenReturn(Optional.of(arrivalStation));
        when(tripRepository.findAllWithStatus()).thenReturn(Arrays.asList(trip));
        when(trainScheduleRepository.findByTrainId(1)).thenReturn(Arrays.asList(departureSchedule, arrivalSchedule));
        when(tripService.findTripsByStationsAndDate(any(TripSearchRequestDTO.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("No trips found for the given stations and date"));
    }

    // TC11: Trip exists, valid schedules, but departure date doesn't match search date
    @Test
    void testSearchTripsWithNonMatchingDate() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().plusDays(2));

        Station departureStation = new Station(1, "Hanoi", "Hà Nội");
        Station arrivalStation = new Station(2, "Saigon", "Hồ Chí Minh");
        Route route = new Route(1, "Hanoi - Saigon");

        BigDecimal x = new BigDecimal("100.5");
        BigDecimal y = new BigDecimal("200.5");

        Train train = new Train(1, "Train1", route);
        Trip trip = new Trip();
        trip.setTripId(1001);
        trip.setTrain(train);
        trip.setBasePrice(BigDecimal.valueOf(500000));
        trip.setTripDate(LocalDate.of(2025, 7, 15));
        trip.setTripStatus("Scheduled");

        // Trip trip = new Trip(1, train, LocalDate.now(), 100.0, "ACTIVE");
        TrainSchedule departureSchedule = new TrainSchedule( departureStation,train,
                LocalTime.of(8, 0), LocalTime.of(0, 0), 1, x, 1);
        TrainSchedule arrivalSchedule = new TrainSchedule(arrivalStation,train,
                LocalTime.of(10, 0), LocalTime.of(0, 0), 2, y, 1);

        when(stationRepository.findByStationName("Hanoi")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Saigon")).thenReturn(Optional.of(arrivalStation));
        when(tripRepository.findAllWithStatus()).thenReturn(Arrays.asList(trip));
        when(trainScheduleRepository.findByTrainId(1)).thenReturn(Arrays.asList(departureSchedule, arrivalSchedule));
        when(tripService.findTripsByStationsAndDate(any(TripSearchRequestDTO.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("No trips found for the given stations and date"));
    }

    // TC12: Valid trip with zero available seats
    @Test
    void testSearchTripsWithZeroSeats() throws Exception {
        TripSearchRequestDTO request = new TripSearchRequestDTO();
        request.setDepartureStation("Hanoi");
        request.setArrivalStation("Saigon");
        request.setTripDate(LocalDate.now().plusDays(1));

        Station departureStation = new Station(1, "Hanoi", "Hà Nội");
        Station arrivalStation = new Station(2, "Saigon", "Hồ Chí Minh");
        Route route = new Route(1, "Hanoi - Saigon");

        BigDecimal x = new BigDecimal("100.5");
        BigDecimal y = new BigDecimal("200.5");

        Train train = new Train(1, "Train1", route);
        Trip trip = new Trip();
        trip.setTripId(1001);
        trip.setTrain(train);
        trip.setBasePrice(BigDecimal.valueOf(500000));
        trip.setTripDate(LocalDate.of(2025, 7, 15));
        trip.setTripStatus("Scheduled");

       // Trip trip = new Trip(1, train, LocalDate.now(), 100.0, "ACTIVE");
        TrainSchedule departureSchedule = new TrainSchedule( departureStation,train,
                LocalTime.of(8, 0), LocalTime.of(0, 0), 1, x, 1);
        TrainSchedule arrivalSchedule = new TrainSchedule(arrivalStation,train,
                LocalTime.of(10, 0), LocalTime.of(0, 0), 2, y, 1);


        when(stationRepository.findByStationName("Hanoi")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Saigon")).thenReturn(Optional.of(arrivalStation));
        when(tripRepository.findAllWithStatus()).thenReturn(Arrays.asList(trip));
        when(trainScheduleRepository.findByTrainId(1)).thenReturn(Arrays.asList(departureSchedule, arrivalSchedule));
        when(seatService.getNumberEmptySeats(1, "Hanoi", "Saigon")).thenReturn(0);

        TripResponseDTO responseDTO = new TripResponseDTO(
                1, "Train1", BigDecimal.valueOf(500), LocalDate.now(), "Scheduled",
                "Hanoi", "Saigon",
                LocalDate.now().plusDays(1).atTime(8, 0),
                LocalDate.now().plusDays(1).atTime(12, 0),
                0
        );
        when(tripService.findTripsByStationsAndDate(any(TripSearchRequestDTO.class)))
                .thenReturn(Arrays.asList(responseDTO));

        mockMvc.perform(post("/api/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tripId").value(1))
                .andExpect(jsonPath("$[0].trainName").value("Train1"))
                .andExpect(jsonPath("$[0].availableSeats").value(0));
    }
}





//import com.tauhoa.train.dtos.request.AddTripRequestDTO;
//import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
//import com.tauhoa.train.dtos.response.TripResponseDTO;
//import com.tauhoa.train.models.Train;
//import com.tauhoa.train.models.Trip;
//import com.tauhoa.train.repositories.TrainRepository;
//import com.tauhoa.train.repositories.TripRepository;
//import com.tauhoa.train.services.TicketService;
//import com.tauhoa.train.services.TripService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//// Unit tests for TripController
//
//@ExtendWith(MockitoExtension.class)
//class TripControllerTest {
//
//    @Mock
//    private TripService tripService;
//
//    @Mock
//    private TripRepository tripRepository;
//
//    @Mock
//    private TrainRepository trainRepository;
//
//    @Mock
//    private TicketService ticketService;
//
//    @InjectMocks
//    private TripController tripController;
//
//    @Test
//    void getTripByDate_ReturnsTripsForValidDate() {
//        LocalDate tripDate = LocalDate.of(2025, 4, 10);
//        List<Trip> trips = List.of(new Trip());
//        when(tripService.findByTripDate(tripDate)).thenReturn(trips);
//
//        ResponseEntity<?> response = tripController.getTripByDate(tripDate);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(trips, response.getBody());
//    }
//
//    @Test
//    void getTripByDate_ReturnsEmptyListForNoTrips() {
//        LocalDate tripDate = LocalDate.of(2025, 4, 10);
//        when(tripService.findByTripDate(tripDate)).thenReturn(List.of());
//
//        ResponseEntity<?> response = tripController.getTripByDate(tripDate);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(((List<?>) response.getBody()).isEmpty());
//    }
//
//    @Test
//    void searchTripsByStationsAndDate_ReturnsTripsForValidRequest() {
//        TripSearchRequestDTO request = new TripSearchRequestDTO("Sài Gòn", "Hà Nội", LocalDate.of(2025, 4, 10));
//        List<TripResponseDTO> trips = List.of(new TripResponseDTO());
//        when(tripService.findTripsByStationsAndDate(request)).thenReturn(trips);
//
//        ResponseEntity<?> response = tripController.searchTripsByStationsAndDate(request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(trips, response.getBody());
//    }
//
//    @Test
//    void searchTripsByStationsAndDate_ReturnsBadRequestForInvalidParameters() {
//        TripSearchRequestDTO request = new TripSearchRequestDTO(null, "Hà Nội", LocalDate.of(2025, 4, 10));
//
//        ResponseEntity<?> response = tripController.searchTripsByStationsAndDate(request);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Invalid parameters: departureStation, arrivalStation, and tripDate are required", response.getBody());
//    }
//
//    @Test
//    void addTrip_ReturnsSuccessForValidRequest() {
//        AddTripRequestDTO request = new AddTripRequestDTO(1, BigDecimal.valueOf(5000), LocalDate.of(2025, 4, 11), 1, 2, 1);
//        Train train = new Train();
//        Trip trip = new Trip();
//        when(trainRepository.findById(request.getTrainId())).thenReturn(Optional.of(train));
//        when(tripRepository.findByTrainAndTripDate(train, request.getTripDate())).thenReturn(List.of());
//        when(tripService.addTrip(request)).thenReturn(trip);
//
//        ResponseEntity<?> response = tripController.addTrip(request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//
//    @Test
//    void addTrip_ReturnsConflictForExistingTrip() {
//        AddTripRequestDTO request = new AddTripRequestDTO(1, BigDecimal.valueOf(5000), LocalDate.of(2025, 7, 11), 1, 2, 1);
//        Train train = new Train();
//        Trip existingTrip = new Trip();
//        when(trainRepository.findById(request.getTrainId())).thenReturn(Optional.of(train));
//        when(tripRepository.findByTrainAndTripDate(train, request.getTripDate())).thenReturn(List.of(existingTrip));
//
//        ResponseEntity<?> response = tripController.addTrip(request);
//
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//
//    @Test
//    void cancelTrip_ReturnsSuccessForValidTripId() {
//        int tripId = 1;
//        doNothing().when(tripService).cancelTrip(tripId);
//        doNothing().when(ticketService).cancelTicketByTrip(tripId);
//
//        ResponseEntity<?> response = tripController.cancelTrip(tripId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//
//    @Test
//    void cancelTrip_ReturnsInternalServerErrorForException() {
//        int tripId = 1;
//        doThrow(new RuntimeException("Error")).when(tripService).cancelTrip(tripId);
//
//        ResponseEntity<?> response = tripController.cancelTrip(tripId);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//}