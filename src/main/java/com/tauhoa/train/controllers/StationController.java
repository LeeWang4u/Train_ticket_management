package com.tauhoa.train.controllers;

import com.tauhoa.train.models.Station;
import com.tauhoa.train.services.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
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

    @GetMapping("station/name")
    public ResponseEntity<?> getStationByStationName(@RequestParam String stationName){ //@PathVariable("id") @RequestParam("id")
        try {
            Optional<Station> existingStation = stationService.findByStationName(stationName);
            return ResponseEntity.ok(existingStation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("station/all")
    public ResponseEntity<List<Station>> getAllStation() {
        List<Station> stations = stationService.getAllStation();
        return ResponseEntity.ok(stations);
    }


    @PostMapping("station")
    public ResponseEntity<Station> createStation(@RequestBody Station station) {
        try {
            Station createdStation = stationService.createStation(station);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Cập nhật thông tin ga
    @PutMapping("station/{id}")
    public ResponseEntity<?> updateStation(@PathVariable("id") int id, @RequestBody Station station) {
        try {
            Station updatedStation = stationService.updateStation(id, station);
            return ResponseEntity.ok(updatedStation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Xóa ga theo ID
    @DeleteMapping("station/{id}")
    public ResponseEntity<?> deleteStation(@PathVariable("id") int id) {
        try {
            boolean isDeleted = stationService.deleteStation(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build(); // Trả về 204 No Content khi xóa thành công
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Station not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
