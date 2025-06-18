package com.tauhoa.train.controllers;

import com.tauhoa.train.common.ApiResponse;
import com.tauhoa.train.dtos.request.AddTripRequestDTO;
import com.tauhoa.train.dtos.response.BookingResponse;
import com.tauhoa.train.dtos.response.ErrorResponse;
import com.tauhoa.train.dtos.response.TripResponseDTO;
import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.models.Station;
import com.tauhoa.train.models.Train;
import com.tauhoa.train.models.Trip;
import com.tauhoa.train.repositories.StationRepository;
import com.tauhoa.train.repositories.TrainRepository;
import com.tauhoa.train.repositories.TripRepository;
import com.tauhoa.train.services.TicketService;
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
@RequestMapping("/api/trips")
public class TripController {
    //@Autowired
    private final TripService tripService;
    private final TripRepository tripRepository;
    private final TrainRepository trainRepository;
    private final TicketService ticketService;
    private final StationRepository stationRepository;

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





    // localhost:8080/trips/searchs
//    {
//        "departureStation": "Huế",
//            "arrivalStation": "Vinh",
//            "tripDate": "2025-04-10"
//    }
    @PostMapping("/searchs")
    public ResponseEntity<?> searchTripsByStationsAndDate(@RequestBody TripSearchRequestDTO request) {
        // Kiểm tra tham số hợp lệ
        if (request.getDepartureStation() == null || request.getArrivalStation() == null || request.getTripDate() == null) {
            return ResponseEntity.badRequest().body("Invalid parameters: departureStation, arrivalStation, and tripDate are required");
        }

        if(request.getDepartureStation().equals(request.getArrivalStation())) {
            return ResponseEntity.badRequest().body("Departure and arrival stations cannot be the same");
        }

        if(request.getTripDate().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().body("Trip date cannot be in the past");
        }

        try {
            Station departureStation = stationRepository.findByStationName(request.getDepartureStation())
                    .orElseThrow(() -> new IllegalArgumentException("Departure station not found: " + request.getDepartureStation()));

            Station arrivalStation = stationRepository.findByStationName(request.getArrivalStation())
                    .orElseThrow(() -> new IllegalArgumentException("Arrival station not found: " + request.getArrivalStation()));

            List<TripResponseDTO> trips = tripService.findTripsByStationsAndDate(request);
            if (trips.isEmpty()) {
                return ResponseEntity.status(500).body("No trips found for the given stations and date");
            }
            return ResponseEntity.ok(trips);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }



//    localhost:8080/trips/add
//{
//    "trainId": 1,
//        "basePrice": 5000,
//        "tripDate": "2025-04-11",
//        "numSoftSeatCarriages": 1,
//        "numSixBerthCarriages": 2,
//        "numFourBerthCarriages": 1
//}
    @PostMapping("/add")
    public ResponseEntity<?> addTrip(@RequestBody AddTripRequestDTO request) {
        try {
            Train train = trainRepository.findById(request.getTrainId())
                    .orElseThrow(() -> new IllegalArgumentException("Train not found for trainId: " + request.getTrainId()));

            LocalDate tripDate = request.getTripDate();
            List<Trip> existingTrips = tripRepository.findByTrainAndTripDate(train, tripDate);
            if (!existingTrips.isEmpty()) {
//                return ResponseEntity.status(409) // Conflict
//                        .body(new ErrorResponse("Tàu này đã được tạo trong ngày này: " + request.getTripDate(), existingTrips.get(0)));
                return ResponseEntity.status(409).body(
                        ApiResponse.failure("Tàu này đã được tạo trong ngày này: " + request.getTripDate(), existingTrips.get(0))
                );
            }
            Trip trip = tripService.addTrip(request);

            // Nếu là chuyến mới tạo
            return ResponseEntity.ok(ApiResponse.success(trip, "Tạo chuyến thành công"));
        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), null));
            return ResponseEntity.badRequest().body(ApiResponse.failure(e.getMessage(), null));
        } catch (Exception e) {
//            return ResponseEntity.status(500).body(new ErrorResponse("An error occurred: " + e.getMessage(), null));
            return ResponseEntity.status(500).body(ApiResponse.failure("Đã xảy ra lỗi: " + e.getMessage(), null));
        }
    }

    // Phương thức getter để truy cập tripRepository (dùng để kiểm tra existingTrips)
    private TripRepository getTripRepository() {
        return tripService.getTripRepository();
    }

    @GetMapping("/cancelTrip/{tripId}")
    public ResponseEntity<?> cancelTrip(@PathVariable("tripId") int tripId) {
        try {
            tripService.cancelTrip(tripId);
            ticketService.cancelTicketByTrip(tripId);
            BookingResponse response = new BookingResponse();
            response.setStatus("success");
            response.setMessage("Hủy giữ vé thành công!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.failure("Đã xảy ra lỗi: " + e.getMessage(), null));
        }
    }
      
    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        if (trips.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/trip")
    public ResponseEntity<List<Trip>> searchTrips(
            @RequestParam(required = false) Integer trainId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        Train train = null;
        if (trainId != null) {
            train = trainRepository.findById(trainId).orElse(null);
        }
        List<Trip> trips = tripService.findTrips(train, fromDate, toDate);
        return ResponseEntity.ok(trips);
    }

    // Xem chi tiết một chuyến đi theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable int id) {
        return tripService.getTripById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
