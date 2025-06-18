package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.request.*;
import com.tauhoa.train.dtos.response.CarriageResponseDTO;
import com.tauhoa.train.dtos.response.CarriageSeatAvailabilityResponseDTO;
import com.tauhoa.train.dtos.response.SeatResponseDTO;
import com.tauhoa.train.dtos.response.TripAvailabilityResponseDTO;
import com.tauhoa.train.services.CarriageListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carriages")
public class CarriageListController {
    private final CarriageListService carriageListService;

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<CarriageResponseDTO>> getCarriagesAndSeatsByTripId(@PathVariable int tripId) {
        List<CarriageResponseDTO> carriages = carriageListService.findCarriagesAndSeatsByTripId(tripId);
        return ResponseEntity.ok(carriages);
    }



//    lấy danh sách ghế của trip, toa,id ga đi, ga đến
//    json:
//    {
//        "tripId": 1,
//            "carriageListId": 1,
//            "departureStationId": 1, // Ga B
//            "arrivalStationId": 4   // Ga D
//    }

    @PostMapping("/seats-availability")
    public ResponseEntity<?> getSeatsAvailabilityByTripIdAndCarriageListId(
            @RequestBody CarriageSeatAvailabilityRequestDTO request) {
        // Kiểm tra tham số hợp lệ
        if (request.getTripId() <= 0 || request.getCarriageListId() <= 0 ||
                request.getDepartureStationId() <= 0 || request.getArrivalStationId() <= 0) {
            return ResponseEntity.badRequest().body("Invalid parameters: all IDs must be greater than 0");
        }

        try {
            CarriageSeatAvailabilityResponseDTO response = carriageListService.findSeatsAvailabilityByTripIdAndCarriageListId(
                    request.getTripId(), request.getCarriageListId(),
                    request.getDepartureStationId(), request.getArrivalStationId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }



//    lấy ds toa và ds ghế dựa trên trip, ga i, ga đến
//    json
//    {
//        "tripId": 1,
//            "departureStationId": 1, // Ga B
//            "arrivalStationId": 4    // Ga D
//    }
    @PostMapping("/seats-availability-by-trip")
    public ResponseEntity<?> getTripWithCarriagesAndSeatsAvailability(
            @RequestBody TripSeatAvailabilityRequestDTO request) {
        // Kiểm tra tham số hợp lệ
        if (request.getTripId() <= 0 || request.getDepartureStationId() <= 0 || request.getArrivalStationId() <= 0) {
            return ResponseEntity.badRequest().body("Invalid parameters: all IDs must be greater than 0");
        }

        try {
            TripAvailabilityResponseDTO response = carriageListService.findTripWithCarriagesAndSeatsAvailability(
                    request.getTripId(), request.getDepartureStationId(), request.getArrivalStationId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


//    json:
//    {
//        "tripId": 1,
//            "departureStation": "Vinh", // Ga B
//            "arrivalStation": "Hà Nội"    // Ga D
//    }
    // lấy danh sách toa, ghế
    @PostMapping("/seats")
    public ResponseEntity<?> getTripWithCarriagesAndSeatsAvailabilityByNameStation(
            @RequestBody TripSeatRequestDTO request) {
//        if (request.getTripId() <= 0 || request.getDepartureStation() == null || request.getArrivalStation() == null
//        || request.getDepartureStation().isBlank() || request.getArrivalStation().isBlank()) {
//            return ResponseEntity.badRequest().body("Invalid parameters: all IDs must be greater than 0");
//        }

        if (request.getTripId() <= 0 ||
                request.getDepartureStation() == null || request.getArrivalStation() == null ||
                request.getDepartureStation().isBlank() || request.getArrivalStation().isBlank()) {

            return ResponseEntity.badRequest().body("Invalid parameters: station names cannot be empty");
        }

        try {
            TripAvailabilityResponseDTO response = carriageListService.findTripWithCarriagesAndSeatsAvailabilityByStationName(
                    request.getTripId(), request.getDepartureStation(), request.getArrivalStation());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
