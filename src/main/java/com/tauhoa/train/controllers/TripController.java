package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.models.Trip;
import com.tauhoa.train.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/timve")
public class TripController {
    @Autowired
    TripService tripService;

    @GetMapping("/ketqua")
    public ResponseEntity<?> getTripByDate(@PathVariable
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                LocalDate tripDate){
        List<Trip> trips = tripService.findByTripDate(tripDate);
        return ResponseEntity.ok(trips);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<TripResponseDTO>> searchTrips(
//            @RequestParam String departureStation,
//            @RequestParam String arrivalStation,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tripDate) {
//        List<TripResponseDTO> trips = tripService.findTrips(departureStation, arrivalStation, tripDate);
//        return ResponseEntity.ok(trips);
//    }

    // tìm tàu dựa trên ga đi, ga đến, tg đi
//    json:
//    {
//        "departureStation": "Sài Gòn",
//            "arrivalStation": "Hà Nội",
//            "tripDate": "2025-04-10"
//    }
//    @PostMapping("/search-trip")
//    public ResponseEntity<List<TripResponseDTO>> searchTrips(@RequestBody TripSearchRequestDTO request) {
//        List<TripResponseDTO> trips = tripService.findTrips(
//                request.getDepartureStation(),
//                request.getArrivalStation(),
//                request.getTripDate()
//        );
//        return ResponseEntity.ok(trips);
//    }

    @PostMapping("/searchs")
    public ResponseEntity<?> searchTripsByStationsAndDate(@RequestBody TripSearchRequestDTO request) {
        // Kiểm tra tham số hợp lệ
        if (request.getDepartureStation() == null || request.getArrivalStation() == null || request.getTripDate() == null) {
            return ResponseEntity.badRequest().body("Invalid parameters: departureStation, arrivalStation, and tripDate are required");
        }

        try {
            List<TripResponseDTO> trips = tripService.findTripsByStationsAndDate(request);
            if (trips.isEmpty()) {
                return ResponseEntity.status(200).body("No trips found for the given stations and date");
            }
            return ResponseEntity.ok(trips);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }



}
