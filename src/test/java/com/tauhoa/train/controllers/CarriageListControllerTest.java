package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.request.TripSeatRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
// Unit tests for TripController
import com.tauhoa.train.dtos.request.CarriageSeatAvailabilityRequestDTO;
import com.tauhoa.train.dtos.response.CarriageResponseDTO;

import com.tauhoa.train.services.CarriageListService;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class CarriageListControllerTest {

    @Mock
    private CarriageListService carriageListService;

    @InjectMocks
    private CarriageListController carriageListController;

    @Test
    void getCarriagesAndSeatsByTripId_ReturnsCarriagesForValidTripId() {
        int tripId = 1;
        List<CarriageResponseDTO> carriages = List.of(new CarriageResponseDTO());
        when(carriageListService.findCarriagesAndSeatsByTripId(tripId)).thenReturn(carriages);

        ResponseEntity<List<CarriageResponseDTO>> response = carriageListController.getCarriagesAndSeatsByTripId(tripId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carriages, response.getBody());
    }

//    @Test
//    void getCarriagesAndSeatsByTripId_ReturnsNotFoundForInvalidTripId() {
//        int tripId = -1;
//        when(carriageListService.findCarriagesAndSeatsByTripId(tripId)).thenThrow(new IllegalArgumentException("Trip not found"));
//
//        ResponseEntity<List<CarriageResponseDTO>> response = carriageListController.getCarriagesAndSeatsByTripId(tripId);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Trip not found", response.getBody());
//    }

//    @Test
//    void getCarriagesAndSeatsByTripId_ReturnsNotFoundForInvalidTripId() {
//        int tripId = -1;
//        when(carriageListService.findCarriagesAndSeatsByTripId(tripId)).thenThrow(new IllegalArgumentException("Trip not found"));
//
//        ResponseEntity<?> response = carriageListController.getCarriagesAndSeatsByTripId(tripId);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Trip not found", response.getBody());
//    }

    @Test
    void getSeatsAvailabilityByTripIdAndCarriageListId_ReturnsNotFoundForNonExistentCarriage() {
        CarriageSeatAvailabilityRequestDTO request = new CarriageSeatAvailabilityRequestDTO(1, 999, 1, 4);
        when(carriageListService.findSeatsAvailabilityByTripIdAndCarriageListId(
                request.getTripId(), request.getCarriageListId(),
                request.getDepartureStationId(), request.getArrivalStationId()))
                .thenThrow(new IllegalArgumentException("Carriage not found"));

        ResponseEntity<?> response = carriageListController.getSeatsAvailabilityByTripIdAndCarriageListId(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Carriage not found", response.getBody());
    }

    @Test
    void getTripWithCarriagesAndSeatsAvailabilityByNameStation_ReturnsNotFoundForInvalidStationNames() {
        TripSeatRequestDTO request = new TripSeatRequestDTO(1, "InvalidStation", "AnotherInvalidStation");
        when(carriageListService.findTripWithCarriagesAndSeatsAvailabilityByStationName(
                request.getTripId(), request.getDepartureStation(), request.getArrivalStation()))
                .thenThrow(new IllegalArgumentException("Stations not found"));

        ResponseEntity<?> response = carriageListController.getTripWithCarriagesAndSeatsAvailabilityByNameStation(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Stations not found", response.getBody());
    }
}