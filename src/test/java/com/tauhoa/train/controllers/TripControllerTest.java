package com.tauhoa.train.controllers;


import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.services.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TripControllerTest {

    @Mock
    private TripService tripService;

    @InjectMocks
    private TripController tripController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tripController).build();
    }

    @Test
    void searchTripsByStationsAndDate_Success() throws Exception {
        // Arrange
        TripResponseDTO tripResponse = new TripResponseDTO(
                1, "SE1", null, LocalDate.of(2025, 4, 10), "Scheduled",
                "Sài Gòn", "Vinh", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 40);
        List<TripResponseDTO> response = List.of(tripResponse);
        when(tripService.findTripsByStationsAndDate(any(TripSearchRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"departureStation\":\"Sài Gòn\",\"arrivalStation\":\"Vinh\",\"tripDate\":\"2025-04-10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tripId").value(1))
                .andExpect(jsonPath("$[0].departureStation").value("Sài Gòn"));
    }

    @Test
    void searchTripsByStationsAndDate_InvalidParams_ReturnsBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/trips/searchs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"departureStation\":null,\"arrivalStation\":\"Vinh\",\"tripDate\":\"2025-04-10\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Invalid parameters: departureStation, arrivalStation, and tripDate are required"));
    }
}
