package com.tauhoa.train.controllers;

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

//    @PostMapping("")
//    public ResponseEntity<?>

    @GetMapping("/ketqua")
    public ResponseEntity<?> getTripByDate(@PathVariable
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                LocalDate tripDate){
        List<Trip> trips = tripService.findByTripDate(tripDate);
        return ResponseEntity.ok(trips);
    }

}
