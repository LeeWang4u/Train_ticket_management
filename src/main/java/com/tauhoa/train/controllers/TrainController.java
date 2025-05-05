package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.response.TrainResponseDTO;
import com.tauhoa.train.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TrainController {
    private final TrainService trainService;
    @GetMapping("train/all")
    public ResponseEntity<List<TrainResponseDTO>> getAllStation() {
        List<TrainResponseDTO> trainResponseDTOS = trainService.getAllTrain();
        return ResponseEntity.ok(trainResponseDTOS);
    }
}
