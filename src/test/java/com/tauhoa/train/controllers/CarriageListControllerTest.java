package com.tauhoa.train.controllers;

//package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.request.CarriageSeatAvailabilityRequestDTO;
import com.tauhoa.train.dtos.response.CarriageResponseDTO;
import com.tauhoa.train.dtos.response.CarriageSeatAvailabilityResponseDTO;
import com.tauhoa.train.dtos.response.TripAvailabilityResponseDTO;
import com.tauhoa.train.services.CarriageListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CarriageListControllerTest {

    @Mock
    private CarriageListService carriageListService;

    @InjectMocks
    private CarriageListController carriageListController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carriageListController).build();
    }

    @Test
    void getCarriagesAndSeatsByTripId_Success() throws Exception {
        // Arrange
        CarriageResponseDTO carriageResponseDTO = new CarriageResponseDTO(1, 1, "Soft Seat", BigDecimal.valueOf(1.0), 40, Collections.emptyList());
        List<CarriageResponseDTO> response = List.of(carriageResponseDTO);
        when(carriageListService.findCarriagesAndSeatsByTripId(1)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/carriages/trip/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].carriageListId").value(1))
                .andExpect(jsonPath("$[0].compartmentName").value("Soft Seat"));
    }

    @Test
    void getSeatsAvailabilityByTripIdAndCarriageListId_InvalidParams_ReturnsBadRequest() throws Exception {
        // Arrange
        CarriageSeatAvailabilityRequestDTO request = new CarriageSeatAvailabilityRequestDTO(1, 1, 1, 4); // tripId = 0

        // Act & Assert
        mockMvc.perform(post("/api/carriages/seats-availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tripId\":0,\"carriageListId\":1,\"departureStationId\":1,\"arrivalStationId\":4}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Invalid parameters: all IDs must be greater than 0"));
    }

    @Test
    void getSeatsAvailabilityByTripIdAndCarriageListId_Success() throws Exception {
        // Arrange
        CarriageSeatAvailabilityResponseDTO response = new CarriageSeatAvailabilityResponseDTO(
                1, 1, "Soft Seat", BigDecimal.valueOf(1.0), 40, Collections.emptyList());
        when(carriageListService.findSeatsAvailabilityByTripIdAndCarriageListId(1, 1, 1, 4)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/carriages/seats-availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tripId\":1,\"carriageListId\":1,\"departureStationId\":1,\"arrivalStationId\":4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carriageListId").value(1))
                .andExpect(jsonPath("$.compartmentName").value("Soft Seat"));
    }

    @Test
    void getSeatsAvailabilityByTripIdAndCarriageListId_NotFound_Returns404() throws Exception {
        // Arrange
        when(carriageListService.findSeatsAvailabilityByTripIdAndCarriageListId(1, 1, 1, 4))
                .thenThrow(new IllegalArgumentException("No carriage found"));

        // Act & Assert
        mockMvc.perform(post("/api/carriages/seats-availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tripId\":1,\"carriageListId\":1,\"departureStationId\":1,\"arrivalStationId\":4}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("No carriage found"));
    }

    @Test
    void getTripWithCarriagesAndSeatsAvailabilityByNameStation_Success() throws Exception {
        // Arrange
        TripAvailabilityResponseDTO response = new TripAvailabilityResponseDTO(1, Collections.emptyList());
        when(carriageListService.findTripWithCarriagesAndSeatsAvailabilityByStationName(1, "Vinh", "Hà Nội"))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/carriages/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tripId\":1,\"departureStation\":\"Vinh\",\"arrivalStation\":\"Hà Nội\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripId").value(1));
    }
}