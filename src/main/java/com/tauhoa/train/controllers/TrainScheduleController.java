package com.tauhoa.train.controllers;

import com.tauhoa.train.models.TrainSchedule;
import com.tauhoa.train.services.ITrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/train-schedules")
public class TrainScheduleController {

    private final ITrainScheduleService trainScheduleService;

    @Autowired
    public TrainScheduleController(ITrainScheduleService trainScheduleService) {
        this.trainScheduleService = trainScheduleService;
    }

    // Lấy tất cả lịch tàu
    @GetMapping
    public List<TrainSchedule> getAllSchedules() {
        return trainScheduleService.getAllSchedules();
    }

    // Tạo mới lịch tàu
    @PostMapping
    public ResponseEntity<TrainSchedule> createSchedule(@RequestBody TrainSchedule schedule) {
        TrainSchedule savedSchedule = trainScheduleService.saveSchedule(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
    }

    // Cập nhật lịch tàu
    @PutMapping("/{id}")
    public ResponseEntity<TrainSchedule> updateSchedule(@PathVariable int id, @RequestBody TrainSchedule schedule) {
        try {
            TrainSchedule updatedSchedule = trainScheduleService.updateSchedule(id, schedule);
            return ResponseEntity.ok(updatedSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Xóa lịch tàu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
        try {
            trainScheduleService.deleteSchedule(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
