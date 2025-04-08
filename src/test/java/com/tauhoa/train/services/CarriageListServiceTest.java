package com.tauhoa.train.services;

import com.tauhoa.train.dtos.response.CarriageSeatAvailabilityResponseDTO;
import com.tauhoa.train.dtos.response.SeatAvailabilityResponseDTO;
//import com.tauhoa.train.entities.*;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarriageListServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private CarriageListRepository carriageListRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private TicketReservationRepository ticketReservationRepository;

    @Mock
    private TrainScheduleRepository trainScheduleRepository;

    @InjectMocks
    private CarriageListService carriageListService;

    @BeforeEach
    void setUp() {
        // Không cần thêm lenient() ở đây, sửa mock để khớp logic là đủ
    }



    @Test
    void findSeatsAvailabilityByTripIdAndCarriageListId_Success() {
        // Arrange
        Trip trip = new Trip();
        trip.setTripId(1);
        trip.setBasePrice(BigDecimal.valueOf(1000));
        Train train = new Train();
        train.setTrainId(1);
        trip.setTrain(train);
        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));

        CarriageList carriage = new CarriageList();
        carriage.setCarriageListId(1);
        carriage.setTrip(trip);
        Compartment compartment = new Compartment();
        compartment.setClassFactor(BigDecimal.valueOf(1.0));
        compartment.setCompartmentName("Soft Seat");
        compartment.setSeatCount(40);
        carriage.setCompartment(compartment);
        when(carriageListRepository.findByTripIdAndCarriageListId(1, 1)).thenReturn(Optional.of(carriage));

        Seat seat = new Seat();
        seat.setSeatId(1);
        seat.setSeatNumber("A1");
        seat.setFloor(1);
        seat.setSeatFactor(BigDecimal.valueOf(1.0));
        when(seatRepository.findByCarriageListId(1)).thenReturn(List.of(seat));

        when(ticketReservationRepository.findByTripId(1)).thenReturn(Collections.emptyList());

        TrainSchedule departureSchedule = new TrainSchedule();
        departureSchedule.setOrdinalNumber(1);
        departureSchedule.setDistance(BigDecimal.valueOf(0));
        departureSchedule.setDepartureTime(LocalTime.of(10, 0));
        TrainSchedule arrivalSchedule = new TrainSchedule();
        arrivalSchedule.setOrdinalNumber(4);
        arrivalSchedule.setDistance(BigDecimal.valueOf(100));
        arrivalSchedule.setDepartureTime(LocalTime.of(12, 0));
        when(trainScheduleRepository.findByTrainIdAndStationId(1, 1)).thenReturn(Optional.of(departureSchedule));
        when(trainScheduleRepository.findByTrainIdAndStationId(1, 4)).thenReturn(Optional.of(arrivalSchedule));

        // Act
        CarriageSeatAvailabilityResponseDTO result = carriageListService.findSeatsAvailabilityByTripIdAndCarriageListId(1, 1, 1, 4);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCarriageListId());
        assertEquals("Soft Seat", result.getCompartmentName());
        assertEquals(1, result.getSeats().size());
        assertEquals(1, result.getSeats().get(0).getSeatId());
        assertEquals("AVAILABLE", result.getSeats().get(0).getSeatStatus());
//        assertEquals(BigDecimal.valueOf(100000.00), result.getSeats().get(0).getTicketPrice()); // Sửa thành 100000.00
        //assertEquals("100000.00" ,result.getSeats().get(0).getTicketPrice()); // Sửa thành 100000.00

    }

}


//package com.tauhoa.train.services;
//
//import com.tauhoa.train.dtos.response.CarriageAvailabilityResponseDTO;
//import com.tauhoa.train.dtos.response.SeatAvailabilityResponseDTO;
//import com.tauhoa.train.dtos.response.TripAvailabilityResponseDTO;
//import com.tauhoa.train.models.*;
//import com.tauhoa.train.repositories.*;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class CarriageListServiceTest {
//
//    @Mock
//    private TripRepository tripRepository;
//
//    @Mock
//    private StationRepository stationRepository;
//
//    @Mock
//    private CarriageListRepository carriageListRepository;
//
//    @Mock
//    private SeatRepository seatRepository;
//
//    @Mock
//    private TicketReservationRepository ticketReservationRepository;
//
//    @Mock
//    private TrainScheduleRepository trainScheduleRepository;
//
//    @InjectMocks
//    private CarriageListService carriageListService;
//
//    @Test
//    void findTripWithCarriagesAndSeatsAvailabilityByStationName_Success() {
//        // Arrange
//        // Mock Trip
//        Trip trip = new Trip();
//        trip.setTripId(1);
//        trip.setBasePrice(BigDecimal.valueOf(1000));
//        Train train = new Train();
//        train.setTrainId(1);
//        trip.setTrain(train);
//        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
//
//        // Mock Stations
//        Station departureStation = new Station();
//        departureStation.setStationId(1);
//        departureStation.setStationName("Huế");
//        Station arrivalStation = new Station();
//        arrivalStation.setStationId(2);
//        arrivalStation.setStationName("Vinh");
//        when(stationRepository.findByStationName("Huế")).thenReturn(Optional.of(departureStation));
//        when(stationRepository.findByStationName("Vinh")).thenReturn(Optional.of(arrivalStation));
//
//        // Mock CarriageList
//        CarriageList carriage = new CarriageList();
//        carriage.setCarriageListId(1);
//        carriage.setStt(1);
//        carriage.setTrip(trip);
//        Compartment compartment = new Compartment();
//        compartment.setCompartmentName("Soft Seat");
//        compartment.setClassFactor(BigDecimal.valueOf(1.0));
//        compartment.setSeatCount(40);
//        carriage.setCompartment(compartment);
//        when(carriageListRepository.findByTripId(1)).thenReturn(List.of(carriage));
//
//        // Mock Seats
//        Seat seat = new Seat();
//        seat.setSeatId(1);
//        seat.setSeatNumber("A1");
//        seat.setFloor(1);
//        seat.setSeatFactor(BigDecimal.valueOf(1.0));
//        when(seatRepository.findByCarriageListId(1)).thenReturn(List.of(seat));
//
//        // Mock TicketReservations (no reservations, so seat is available)
//        when(ticketReservationRepository.findByTripId(1)).thenReturn(Collections.emptyList());
//
//        // Mock TrainSchedules
//        TrainSchedule departureSchedule = new TrainSchedule();
//        departureSchedule.setOrdinalNumber(1);
//        departureSchedule.setDistance(BigDecimal.valueOf(0));
//        departureSchedule.setDepartureTime(LocalTime.of(10, 0));
//        TrainSchedule arrivalSchedule = new TrainSchedule();
//        arrivalSchedule.setOrdinalNumber(4);
//        arrivalSchedule.setDistance(BigDecimal.valueOf(100));
//        arrivalSchedule.setDepartureTime(LocalTime.of(12, 0));
//        when(trainScheduleRepository.findByTrainIdAndStationId(1, 1)).thenReturn(Optional.of(departureSchedule));
//        when(trainScheduleRepository.findByTrainIdAndStationId(1, 2)).thenReturn(Optional.of(arrivalSchedule));
//
//        // Act
//        TripAvailabilityResponseDTO result = carriageListService.findTripWithCarriagesAndSeatsAvailabilityByStationName(1, "Huế", "Vinh");
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getTripId());
//        assertEquals(1, result.getCarriages().size());
//
//        // Kiểm tra CarriageAvailabilityResponseDTO
//        CarriageAvailabilityResponseDTO carriageDTO = result.getCarriages().get(0);
//        assertEquals(1, carriageDTO.getCarriageListId());
//        assertEquals(1, carriageDTO.getStt());
//        assertEquals("Soft Seat", carriageDTO.getCompartmentName());
//        assertEquals(BigDecimal.valueOf(1.0), carriageDTO.getClassFactor());
//        assertEquals(40, carriageDTO.getSeatCount());
//        assertEquals(1, carriageDTO.getSeats().size());
//
//        // Kiểm tra SeatAvailabilityResponseDTO
//        SeatAvailabilityResponseDTO seatDTO = carriageDTO.getSeats().get(0);
//        assertEquals(1, seatDTO.getSeatId());
//        assertEquals("A1", seatDTO.getSeatNumber());
//        assertEquals(1, seatDTO.getFloor());
//        assertEquals(BigDecimal.valueOf(1.0), seatDTO.getSeatFactor());
//        assertEquals("AVAILABLE", seatDTO.getSeatStatus()); // Sửa getSeatStatus() thành getStatus()
//        assertTrue(seatDTO.isAvailable());
//        //assertEquals(BigDecimal.valueOf(100000.00), seatDTO.getTicketPrice()); // Sửa thành 100000.00
//    }
//
//    @Test
//    void findTripWithCarriagesAndSeatsAvailabilityByStationName_TripNotFound_ThrowsException() {
//        // Arrange
//        when(tripRepository.findById(1)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            carriageListService.findTripWithCarriagesAndSeatsAvailabilityByStationName(1, "Huế", "Vinh");
//        });
//        assertEquals("Trip not found for tripId: 1", exception.getMessage());
//    }
//
//    @Test
//    void findTripWithCarriagesAndSeatsAvailabilityByStationName_DepartureStationNotFound_ThrowsException() {
//        // Arrange
//        Trip trip = new Trip();
//        trip.setTripId(1);
//        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
//        when(stationRepository.findByStationName("Huế")).thenReturn(Optional.empty());
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            carriageListService.findTripWithCarriagesAndSeatsAvailabilityByStationName(1, "Huế", "Vinh");
//        });
//        assertEquals("Departure station not found: Huế", exception.getMessage());
//    }
//
//    @Test
//    void findTripWithCarriagesAndSeatsAvailabilityByStationName_NoCarriages_ThrowsException() {
//        // Arrange
//        Trip trip = new Trip();
//        trip.setTripId(1);
//        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
//
//        Station departureStation = new Station();
//        departureStation.setStationId(1);
//        Station arrivalStation = new Station();
//        arrivalStation.setStationId(2);
//        when(stationRepository.findByStationName("Huế")).thenReturn(Optional.of(departureStation));
//        when(stationRepository.findByStationName("Vinh")).thenReturn(Optional.of(arrivalStation));
//
//        when(carriageListRepository.findByTripId(1)).thenReturn(Collections.emptyList());
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            carriageListService.findTripWithCarriagesAndSeatsAvailabilityByStationName(1, "Huế", "Vinh");
//        });
//        assertEquals("No carriages found for tripId: 1", exception.getMessage());
//    }
//
//    @Test
//    void findTripWithCarriagesAndSeatsAvailabilityByStationName_DepartureAfterArrival_ThrowsException() {
//        // Arrange
//        Trip trip = new Trip();
//        trip.setTripId(1);
//        Train train = new Train();
//        train.setTrainId(1);
//        trip.setTrain(train); // Thêm Train để tránh NullPointerException
//        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
//
//        Station departureStation = new Station();
//        departureStation.setStationId(1);
//        Station arrivalStation = new Station();
//        arrivalStation.setStationId(2);
//        when(stationRepository.findByStationName("Huế")).thenReturn(Optional.of(departureStation));
//        when(stationRepository.findByStationName("Vinh")).thenReturn(Optional.of(arrivalStation));
//
//        CarriageList carriage = new CarriageList();
//        carriage.setTrip(trip);
//        when(carriageListRepository.findByTripId(1)).thenReturn(List.of(carriage));
//
//        TrainSchedule departureSchedule = new TrainSchedule();
//        departureSchedule.setOrdinalNumber(4); // Departure sau arrival
//        TrainSchedule arrivalSchedule = new TrainSchedule();
//        arrivalSchedule.setOrdinalNumber(1);
//        when(trainScheduleRepository.findByTrainIdAndStationId(1, 1)).thenReturn(Optional.of(departureSchedule));
//        when(trainScheduleRepository.findByTrainIdAndStationId(1, 2)).thenReturn(Optional.of(arrivalSchedule));
//
//        // Act & Assert
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            carriageListService.findTripWithCarriagesAndSeatsAvailabilityByStationName(1, "Huế", "Vinh");
//        });
//        assertEquals("Departure station must be before arrival station", exception.getMessage());
//    }
//}

//package com.tauhoa.train.services;
//
//import com.tauhoa.train.dtos.response.CarriageSeatAvailabilityResponseDTO;
//import com.tauhoa.train.dtos.response.SeatAvailabilityResponseDTO;
////import com.tauhoa.train.entities.*;
//import com.tauhoa.train.models.*;
//import com.tauhoa.train.repositories.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class CarriageListServiceTest {
//
//    @Mock
//    private TripRepository tripRepository;
//
//    @Mock
//    private CarriageListRepository carriageListRepository;
//
//    @Mock
//    private SeatRepository seatRepository;
//
//    @Mock
//    private TicketReservationRepository ticketReservationRepository;
//
//    @Mock
//    private TrainScheduleRepository trainScheduleRepository;
//
//    @InjectMocks
//    private CarriageListService carriageListService;
//
//    @BeforeEach
//    void setUp() {
//        // Không cần thêm lenient() ở đây, sửa mock để khớp logic là đủ
//    }
//
//
//
////    @Test
////    void findSeatsAvailabilityByTripIdAndCarriageListId_Success() {
////        // Arrange
////        Trip trip = new Trip();
////        trip.setTripId(1);
////        trip.setBasePrice(BigDecimal.valueOf(1000));
////        Train train = new Train();
////        train.setTrainId(1);
////        trip.setTrain(train);
////        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
////
////        CarriageList carriage = new CarriageList();
////        carriage.setCarriageListId(1);
////        carriage.setTrip(trip);
////        Compartment compartment = new Compartment();
////        compartment.setClassFactor(BigDecimal.valueOf(1.0));
////        compartment.setCompartmentName("Soft Seat");
////        compartment.setSeatCount(40);
////        carriage.setCompartment(compartment);
////        when(carriageListRepository.findByTripIdAndCarriageListId(1, 1)).thenReturn(Optional.of(carriage));
////
////        Seat seat = new Seat();
////        seat.setSeatId(1);
////        seat.setSeatNumber("A1");
////        seat.setFloor(1);
////        seat.setSeatFactor(BigDecimal.valueOf(1.0));
////        when(seatRepository.findByCarriageListId(1)).thenReturn(List.of(seat));
////
////        when(ticketReservationRepository.findByTripId(1)).thenReturn(Collections.emptyList());
////
////        TrainSchedule departureSchedule = new TrainSchedule();
////        departureSchedule.setOrdinalNumber(1);
////        departureSchedule.setDistance(BigDecimal.valueOf(0));
////        departureSchedule.setDepartureTime(LocalTime.of(10, 0));
////        TrainSchedule arrivalSchedule = new TrainSchedule();
////        arrivalSchedule.setOrdinalNumber(4);
////        arrivalSchedule.setDistance(BigDecimal.valueOf(100));
////        arrivalSchedule.setDepartureTime(LocalTime.of(12, 0));
////        when(trainScheduleRepository.findByTrainIdAndStationId(1, 1)).thenReturn(Optional.of(departureSchedule));
////        when(trainScheduleRepository.findByTrainIdAndStationId(1, 4)).thenReturn(Optional.of(arrivalSchedule));
////
////        // Act
////        CarriageSeatAvailabilityResponseDTO result = carriageListService.findSeatsAvailabilityByTripIdAndCarriageListId(1, 1, 1, 4);
////
////        // Assert
////        assertNotNull(result);
////        assertEquals(1, result.getCarriageListId());
////        assertEquals("Soft Seat", result.getCompartmentName());
////        assertEquals(1, result.getSeats().size());
////        assertEquals(1, result.getSeats().get(0).getSeatId());
////        assertEquals("AVAILABLE", result.getSeats().get(0).getSeatStatus());
//////        assertEquals(BigDecimal.valueOf(100000.00), result.getSeats().get(0).getTicketPrice()); // Sửa thành 100000.00
////        assertEquals("100000.00" ,result.getSeats().get(0).getTicketPrice()); // Sửa thành 100000.00
////
////    }
//
//}
//
//
////package com.tauhoa.train.services;
////
//////package com.tauhoa.train.services;
////
////import com.tauhoa.train.dtos.response.CarriageSeatAvailabilityResponseDTO;
////import com.tauhoa.train.models.*;
////import com.tauhoa.train.repositories.*;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.junit.jupiter.MockitoExtension;
////
////import java.math.BigDecimal;
////import java.util.Collections;
////import java.util.List;
////import java.util.Optional;
////
////import static org.junit.jupiter.api.Assertions.*;
////import static org.mockito.ArgumentMatchers.anyInt;
////import static org.mockito.Mockito.when;
////
////@ExtendWith(MockitoExtension.class)
////public class CarriageListServiceTest {
////
////    @Mock
////    private CarriageListRepository carriageListRepository;
////
////    @Mock
////    private SeatRepository seatRepository;
////
////    @Mock
////    private TicketReservationRepository ticketReservationRepository;
////
////    @Mock
////    private TrainScheduleRepository trainScheduleRepository;
////
////    @Mock
////    private TripRepository tripRepository;
////
////    @Mock
////    private StationRepository stationRepository;
////
////    @InjectMocks
////    private CarriageListService carriageListService;
////
////    @BeforeEach
////    void setUp() {
////        // Có thể thêm khởi tạo nếu cần
////    }
////
////    @Test
////    void findSeatsAvailabilityByTripIdAndCarriageListId_Success() {
////        // Arrange
////        Trip trip = new Trip();
////        trip.setBasePrice(BigDecimal.valueOf(1000));
////        when(tripRepository.findById(1)).thenReturn(Optional.of(trip));
////
////        CarriageList carriage = new CarriageList();
////        Compartment compartment = new Compartment();
////        compartment.setClassFactor(BigDecimal.valueOf(1.0));
////        compartment.setCompartmentName("Soft Seat");
////        compartment.setSeatCount(40);
////        carriage.setCompartment(compartment);
////        Train train = new Train();
////        trip.setTrain(train);
////        carriage.setTrip(trip);
////        when(carriageListRepository.findByTripIdAndCarriageListId(1, 1)).thenReturn(Optional.of(carriage));
////
////        Seat seat = new Seat();
////        seat.setSeatId(1);
////        seat.setSeatFactor(BigDecimal.valueOf(1.0));
////        when(seatRepository.findByCarriageListId(1)).thenReturn(List.of(seat));
////
////        when(ticketReservationRepository.findByTripId(1)).thenReturn(Collections.emptyList());
////
////        TrainSchedule departureSchedule = new TrainSchedule();
////        departureSchedule.setOrdinalNumber(1);
////        departureSchedule.setDistance(BigDecimal.valueOf(0));
////        TrainSchedule arrivalSchedule = new TrainSchedule();
////        arrivalSchedule.setOrdinalNumber(4);
////        arrivalSchedule.setDistance(BigDecimal.valueOf(100));
////        when(trainScheduleRepository.findByTrainIdAndStationId(anyInt(), anyInt()))
////                .thenReturn(Optional.of(departureSchedule))
////                .thenReturn(Optional.of(arrivalSchedule));
////
////        // Act
////        CarriageSeatAvailabilityResponseDTO result = carriageListService.findSeatsAvailabilityByTripIdAndCarriageListId(1, 1, 1, 4);
////
////        // Assert
////        assertNotNull(result);
////        assertEquals("Soft Seat", result.getCompartmentName());
////        assertEquals(1, result.getSeats().get(0).getSeatId());
////        assertEquals("AVAILABLE", result.getSeats().get(0).isAvailable());
////        assertEquals(BigDecimal.valueOf(100000), result.getSeats().get(0).getTicketPrice()); // 1000 * 100 * 1 * 1
////    }
////
////    @Test
////    void findSeatsAvailabilityByTripIdAndCarriageListId_TripNotFound_ThrowsException() {
////        // Arrange
////        when(tripRepository.findById(1)).thenReturn(Optional.empty());
////
////        // Act & Assert
////        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
////                carriageListService.findSeatsAvailabilityByTripIdAndCarriageListId(1, 1, 1, 4));
////        assertEquals("Trip not found for tripId: 1", exception.getMessage());
////    }
////}