package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.AddTripRequestDTO;
import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.models.*;
import com.tauhoa.train.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @InjectMocks
    private TripService tripService;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private TrainScheduleRepository trainScheduleRepository;
    @Mock
    private TripService tripServiceSpy; // để mock getNumberEmptySeats nếu là method riêng
    @Mock
    private SeatService seatService; // ✅ mock đúng đối tượng đã tách ra
    @Mock
    private TrainRepository trainRepository;
    @Mock
    private CompartmentRepository compartmentRepository;
    @Mock
    private CarriageListRepository carriageListRepository;
    @Mock
    private SeatRepository seatRepository;


    @Test
    void findTripsByStationsAndDate() {
    }

    @Test
    void testFindTripsByStationsAndDate_ReturnsTripResponseDTO() {
        // Given
        TripSearchRequestDTO request = new TripSearchRequestDTO("Hà Nội", "Sài Gòn", LocalDate.of(2025, 6, 15));
//        request.setDepartureStation("Hà Nội");
//        request.setArrivalStation("Đà Nẵng");
//        request.setTripDate(LocalDate.of(2025, 6, 20));

        Station departureStation = new Station(1, "Hà Nội", "Hà Nội");
        Station arrivalStation = new Station(2, "Sài Gòn", "Hồ Chí Minh");

        Route route = new Route(1, "Hà Nội - Sài Gòn");

        Train train = new Train(1, "SE1", route);

        Trip trip = new Trip();
        trip.setTripId(1001);
        trip.setTrain(train);
        trip.setBasePrice(BigDecimal.valueOf(500000));
        trip.setTripDate(LocalDate.of(2025, 6, 15));
        trip.setTripStatus("ON_TIME");

        BigDecimal x = new BigDecimal("100.5");
        BigDecimal y = new BigDecimal("200.5");

        TrainSchedule depSchedule = new TrainSchedule( departureStation,train,
                LocalTime.of(8, 0), LocalTime.of(0, 0), 1, x, 1);
        TrainSchedule arrSchedule = new TrainSchedule(arrivalStation,train,
                LocalTime.of(10, 0), LocalTime.of(0, 0), 2, y, 1);

        when(stationRepository.findByStationName("Hà Nội")).thenReturn(Optional.of(departureStation));
        when(stationRepository.findByStationName("Sài Gòn")).thenReturn(Optional.of(arrivalStation));
        when(tripRepository.findAllWithStatus()).thenReturn(List.of(trip));
        when(trainScheduleRepository.findByTrainId(1)).thenReturn(List.of(depSchedule, arrSchedule));

        // Nếu getNumberEmptySeats là hàm protected hoặc private khác, mock như sau:
      //  doReturn(100).when(seatService).getNumberEmptySeats(11, "Hà Nội", "Sài Gòn");
        when(seatService.getNumberEmptySeats(1001, "Hà Nội", "Sài Gòn")).thenReturn(100);
        // When
        List<TripResponseDTO> results = tripService.findTripsByStationsAndDate(request);

        // Then
        assertEquals(1, results.size());
        TripResponseDTO dto = results.get(0);
        assertEquals("SE1", dto.getTrainName());
        assertEquals("Hà Nội", dto.getDepartureStation());
        assertEquals("Sài Gòn", dto.getArrivalStation());
        assertEquals(100, dto.getAvailableSeats());
    }

    @Test
    void testFindTripsByStationsAndDate_ThrowsIfDepartureStationNotFound() {
        TripSearchRequestDTO request = new TripSearchRequestDTO("Sai Ga","Đà Nẵng",LocalDate.of(2025, 6, 15));
//        request.setDepartureStation("Sai Ga");
//        request.setArrivalStation("Đà Nẵng");
//        request.setTripDate(LocalDate.now());

        when(stationRepository.findByStationName("Sai Ga")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> tripService.findTripsByStationsAndDate(request));
    }

    @Test
    void addTrip_CreatesTripWithCarriagesAndSeats() {
        // Arrange
        AddTripRequestDTO request = new AddTripRequestDTO();
        request.setTrainId(1);
        request.setTripDate(LocalDate.of(2025, 6, 20));
        request.setBasePrice(BigDecimal.valueOf(500000));
        request.setNumSoftSeatCarriages(1);
        request.setNumSixBerthCarriages(1);
        request.setNumFourBerthCarriages(1);

        Train train = new Train();
        train.setTrainId(1);

        when(trainRepository.findById(1)).thenReturn(Optional.of(train));

        // Mô phỏng Trip được save và gán ID
        Trip savedTrip = new Trip();
        savedTrip.setTripId(101);
        savedTrip.setTrain(train);
        savedTrip.setTripDate(request.getTripDate());
        savedTrip.setBasePrice(request.getBasePrice());
        savedTrip.setTripStatus("Scheduled");

        when(tripRepository.save(any(Trip.class))).thenReturn(savedTrip);

        // Giả lập các khoang (compartments)
        Compartment softSeat = new Compartment();
        softSeat.setCompartmentId(1);
        softSeat.setCompartmentName("Soft Seat");
        softSeat.setSeatCount(40); // 10 hàng * 4 ghế

        Compartment sixBerth = new Compartment();
        sixBerth.setCompartmentId(2);
        sixBerth.setCompartmentName("6-Berth");
        sixBerth.setSeatCount(18); // 6 hàng * 3 tầng

        Compartment fourBerth = new Compartment();
        fourBerth.setCompartmentId(3);
        fourBerth.setCompartmentName("4-Berth");
        fourBerth.setSeatCount(12); // 6 hàng * 2 tầng

        when(compartmentRepository.findById(1)).thenReturn(Optional.of(softSeat));
        when(compartmentRepository.findById(2)).thenReturn(Optional.of(sixBerth));
        when(compartmentRepository.findById(3)).thenReturn(Optional.of(fourBerth));

        // Act
        Trip result = tripService.addTrip(request);

        // Assert Trip thông tin cơ bản
        assertNotNull(result);
        assertEquals(101, result.getTripId());
        assertEquals(train, result.getTrain());
        assertEquals(request.getBasePrice(), result.getBasePrice());
        assertEquals(request.getTripDate(), result.getTripDate());

        // Verify Trip saved
        verify(tripRepository, times(1)).save(any(Trip.class));

        // Verify Carriages saved
        ArgumentCaptor<List<CarriageList>> carriageCaptor = ArgumentCaptor.forClass(List.class);
        verify(carriageListRepository).saveAll(carriageCaptor.capture());
        List<CarriageList> carriages = carriageCaptor.getValue();
        assertEquals(3, carriages.size()); // 1 soft + 1 six + 1 four

        // Verify Seats saved
        ArgumentCaptor<List<Seat>> seatCaptor = ArgumentCaptor.forClass(List.class);
        verify(seatRepository, times(3)).saveAll(seatCaptor.capture());

        List<List<Seat>> seatBatches = seatCaptor.getAllValues();
        assertEquals(3, seatBatches.size());

        // Kiểm tra chi tiết số lượng ghế theo từng compartment
        int softSeatCount = seatBatches.get(0).size();
        int sixBerthCount = seatBatches.get(1).size();
        int fourBerthCount = seatBatches.get(2).size();

        assertEquals(40, softSeatCount);     // 10 hàng x 4 ghế (A-D)
        assertEquals(18, sixBerthCount);     // 6 hàng x 3 tầng (A, B, C)
        assertEquals(12, fourBerthCount);    // 6 hàng x 2 tầng (A, B)
    }


    @Test
    void getNumberEmptySeats() {
    }

    @Test
    void addTrip() {
    }
}