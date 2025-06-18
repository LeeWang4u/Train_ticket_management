package com.tauhoa.train.services;

import static org.junit.jupiter.api.Assertions.*;

import com.tauhoa.train.controllers.CarriageListController;
import com.tauhoa.train.dtos.request.CarriageSeatAvailabilityRequestDTO;
import com.tauhoa.train.dtos.request.TripSeatAvailabilityRequestDTO;
import com.tauhoa.train.dtos.request.TripSeatRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.tauhoa.train.dtos.response.CarriageResponseDTO;
import com.tauhoa.train.dtos.response.CarriageSeatAvailabilityResponseDTO;
import com.tauhoa.train.services.CarriageListService;

@ExtendWith(MockitoExtension.class)
class CarriageListServiceTest {

    @Mock
    private CarriageListService carriageListService;

    @InjectMocks
    private CarriageListController carriageListController;


    @Test
    void findTripWithCarriagesAndSeatsAvailabilityByStationName_ReturnsBadRequestForEmptyStationNames() {
        TripSeatRequestDTO request = new TripSeatRequestDTO(1, "", "");

        ResponseEntity<?> response = carriageListController.getTripWithCarriagesAndSeatsAvailabilityByNameStation(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid parameters: station names cannot be empty", response.getBody());
    }

}

