package com.tauhoa.train.controllers;

import com.tauhoa.train.models.Station;
import com.tauhoa.train.services.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor
@RequiredArgsConstructor
@RestController
public class StationController {

    private  final StationService stationService;

    @GetMapping("/station/{id}")
    public ResponseEntity<?> getStation(@PathVariable("id") int id){ //@PathVariable("id") @RequestParam("id")
        try {
            Station existingStation = stationService.getStation(id);
            return ResponseEntity.ok(existingStation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("station/suggest")
    public ResponseEntity<List<Station>> getStationSuggestions(@RequestParam String keyword) {
        List<Station> stations = stationService.findStationsByKeyword(keyword);
        return ResponseEntity.ok(stations);
    }
}
