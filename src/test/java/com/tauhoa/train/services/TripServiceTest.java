package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private TrainScheduleRepository trainScheduleRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private TicketReservationRepository ticketReservationRepository;

    @Mock
    private CarriageListRepository carriageListRepository;

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private CompartmentRepository compartmentRepository;

    @InjectMocks
    private TripService tripService;

    @BeforeEach
    void setUp() {
        // Có thể thêm khởi tạo nếu cần
    }

    @Test
    void findTripsByStationsAndDate_Success() {
        // Arrange
        Trip trip = new Trip();
        trip.setTripId(1);
        Train train = new Train();
        train.setTrainId(1);
        train.setTrainName("SE1");
        trip.setTrain(train);
        trip.setTripDate(LocalDate.of(2025, 4, 10));
        when(tripRepository.findAll()).thenReturn(List.of(trip));
        when(tripRepository.findById(1)).thenReturn(Optional.of(trip)); // Thêm mock này

        Station departureStation = new Station();
        departureStation.setStationId(1);
        departureStation.setStationName("Huế");
        Station arrivalStation = new Station();
        arrivalStation.setStationId(2);
        arrivalStation.setStationName("Vinh");
        when(stationRepository.findByStationName("Huế")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Vinh")).thenReturn(Optional.of(arrivalStation));

        TrainSchedule departureSchedule = new TrainSchedule();
        departureSchedule.setStation(departureStation);
        departureSchedule.setOrdinalNumber(1);
        departureSchedule.setDay(1);
        departureSchedule.setDepartureTime(LocalTime.of(10, 0));
        TrainSchedule arrivalSchedule = new TrainSchedule();
        arrivalSchedule.setStation(arrivalStation);
        arrivalSchedule.setOrdinalNumber(2);
        arrivalSchedule.setDay(1);
        arrivalSchedule.setDepartureTime(LocalTime.of(12, 0));
        arrivalSchedule.setArrivalTime(LocalTime.of(12, 30));
        when(trainScheduleRepository.findByTrainId(1)).thenReturn(List.of(departureSchedule, arrivalSchedule));
        when(trainScheduleRepository.findByTrainIdAndStationId(1, 1)).thenReturn(Optional.of(departureSchedule));
        when(trainScheduleRepository.findByTrainIdAndStationId(1, 2)).thenReturn(Optional.of(arrivalSchedule));

        CarriageList carriage = new CarriageList();
        Compartment compartment = new Compartment();
        compartment.setSeatCount(40);
        carriage.setCompartment(compartment);
        when(carriageListRepository.findByTripId(1)).thenReturn(List.of(carriage));
        when(ticketReservationRepository.findByTripId(1)).thenReturn(Collections.emptyList());

        TripSearchRequestDTO request = new TripSearchRequestDTO("Huế", "Vinh", LocalDate.of(2025, 4, 10));

        // Act
        List<TripResponseDTO> result = tripService.findTripsByStationsAndDate(request);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("SE1", result.get(0).getTrainName());
        assertEquals(40, result.get(0).getAvailableSeats());
        assertEquals(LocalDateTime.of(2025, 4, 10, 10, 0), result.get(0).getDepartureTime());
        assertEquals(LocalDateTime.of(2025, 4, 10, 12, 30), result.get(0).getArrivalTime());
    }
//    @Test
//    void findTripsByStationsAndDate_Success() {
//        // Arrange
//        Trip trip = new Trip();
//        Train train = new Train();
//        train.setTrainId(1);
//        train.setTrainName("SE1");
//        trip.setTrain(train);
//        trip.setTripDate(LocalDate.of(2025, 4, 10));
//        when(tripRepository.findAll()).thenReturn(List.of(trip));
//
//        Station departureStation = new Station();
//        departureStation.setStationId(1);
//        departureStation.setStationName("Huế");
//        Station arrivalStation = new Station();
//        arrivalStation.setStationId(2);
//        arrivalStation.setStationName("Vinh");
//        when(stationRepository.findByStationName("Huế")).thenReturn(Optional.of(departureStation));
//        when(stationRepository.findByStationName("Vinh")).thenReturn(Optional.of(arrivalStation));
//
//        TrainSchedule departureSchedule = new TrainSchedule();
//        departureSchedule.setStation(departureStation);
//        departureSchedule.setOrdinalNumber(1);
//        departureSchedule.setDepartureTime(LocalTime.of(10, 0));
//        TrainSchedule arrivalSchedule = new TrainSchedule();
//        arrivalSchedule.setStation(arrivalStation);
//        arrivalSchedule.setOrdinalNumber(2);
//        arrivalSchedule.setDepartureTime(LocalTime.of(12, 0));
//        when(trainScheduleRepository.findByTrainId(1)).thenReturn(List.of(departureSchedule, arrivalSchedule));
//
//        CarriageList carriage = new CarriageList();
//        Compartment compartment = new Compartment();
//        compartment.setSeatCount(40);
//        carriage.setCompartment(compartment);
//        when(carriageListRepository.findByTripId(anyInt())).thenReturn(List.of(carriage));
//        when(ticketReservationRepository.findByTripId(anyInt())).thenReturn(Collections.emptyList());
//
//        TripSearchRequestDTO request = new TripSearchRequestDTO("Huế", "Vinh", LocalDate.of(2025, 4, 10));
//
//        // Act
//        List<TripResponseDTO> result = tripService.findTripsByStationsAndDate(request);
//
//        // Assert
//        assertFalse(result.isEmpty());
//        assertEquals("SE1", result.get(0).getTrainName());
//        assertEquals(40, result.get(0).getAvailableSeats());
//    }

    @Test
    void getNumberEmptySeats_Success() {
        // Arrange
        Trip trip = new Trip();
        Train train = new Train();
        train.setTrainId(1);
        trip.setTrain(train);
        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));

        Station departureStation = new Station();
        departureStation.setStationId(1);
        Station arrivalStation = new Station();
        arrivalStation.setStationId(2);
        when(stationRepository.findByStationName("Huế")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Vinh")).thenReturn(Optional.of(arrivalStation));

        TrainSchedule departureSchedule = new TrainSchedule();
        departureSchedule.setOrdinalNumber(1);
        TrainSchedule arrivalSchedule = new TrainSchedule();
        arrivalSchedule.setOrdinalNumber(2);
        when(trainScheduleRepository.findByTrainIdAndStationId(1, 1)).thenReturn(Optional.of(departureSchedule));
        when(trainScheduleRepository.findByTrainIdAndStationId(1, 2)).thenReturn(Optional.of(arrivalSchedule));

        CarriageList carriage = new CarriageList();
        Compartment compartment = new Compartment();
        compartment.setSeatCount(40);
        carriage.setCompartment(compartment);
        when(carriageListRepository.findByTripId(1)).thenReturn(List.of(carriage));

        when(ticketReservationRepository.findByTripId(1)).thenReturn(Collections.emptyList());

        // Act
        int result = tripService.getNumberEmptySeats(1, "Huế", "Vinh");

        // Assert
        assertEquals(40, result);
    }
}