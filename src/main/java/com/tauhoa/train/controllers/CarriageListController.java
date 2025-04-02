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

    @PostMapping("/trip")
    public ResponseEntity<?> getCarriagesAndSeatsByTripId(@RequestBody TripIdRequestDTO request) {
        // Kiểm tra tripId hợp lệ
        if (request.getTripId() <= 0) {
            return ResponseEntity.badRequest().body("Invalid tripId: must be greater than 0");
        }

        List<CarriageResponseDTO> carriages = carriageListService.findCarriagesAndSeatsByTripId(request.getTripId());
        if (carriages.isEmpty()) {
            return ResponseEntity.status(404).body("No carriages found for trip ID: " + request.getTripId());
        }
        return ResponseEntity.ok(carriages);
    }

    // lấy danh sách ghế dựa trên id trip và stt của toa
    // json
//    {
//        "tripId": 1,
//            "stt": 1
//    }
    @PostMapping("/seats")
    public ResponseEntity<?> getSeatsByTripIdAndStt(@RequestBody CarriageSeatRequestDTO request) {
        // Kiểm tra tham số hợp lệ
        if (request.getTripId() <= 0 || request.getStt() <= 0) {
            return ResponseEntity.badRequest().body("Invalid tripId or stt: must be greater than 0");
        }

        try {
            List<SeatResponseDTO> seats = carriageListService.findSeatsByTripIdAndStt(request.getTripId(), request.getStt());
            if (seats.isEmpty()) {
                return ResponseEntity.status(404).body("No seats found for tripId: " + request.getTripId() + " and stt: " + request.getStt());
            }
            return ResponseEntity.ok(seats);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

// lấy danh sách ghế của toa dựa theo id trip và id toa
    // json:
//{
//    "tripId": 1,
//        "carriageListId": 1
//}
    @PostMapping("/seats-by-id")
    public ResponseEntity<?> getSeatsByTripIdAndCarriageListId(@RequestBody CarriageSeatByIdRequestDTO request) {
        // Kiểm tra tham số hợp lệ
        if (request.getTripId() <= 0 || request.getCarriageListId() <= 0) {
            return ResponseEntity.badRequest().body("Invalid tripId or carriageListId: must be greater than 0");
        }

        try {
            List<SeatResponseDTO> seats = carriageListService.findSeatsByTripIdAndCarriageListId(
                    request.getTripId(), request.getCarriageListId());
            if (seats.isEmpty()) {
                return ResponseEntity.status(404).body(
                        "No seats found for tripId: " + request.getTripId() + " and carriageListId: " + request.getCarriageListId());
            }
            return ResponseEntity.ok(seats);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
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
}
