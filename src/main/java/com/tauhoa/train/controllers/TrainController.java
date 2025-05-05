package com.tauhoa.train.controllers;

import com.tauhoa.train.common.ApiResponse;
import com.tauhoa.train.dtos.request.AddTrainRequestDTO;
import com.tauhoa.train.dtos.request.TrainRequestDTO;
import com.tauhoa.train.dtos.response.TrainResponseDTO;
import com.tauhoa.train.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("train/add")
    public ResponseEntity<ApiResponse<?>> addTrain(@RequestBody AddTrainRequestDTO dto) {
        ApiResponse<?> response = trainService.addTrain(dto);

        if (!response.isSuccess()) {
            // Xác định lỗi cụ thể để trả đúng HTTP status
            if ("Train name already exists".equals(response.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409
            } else if ("Route not found".equals(response.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 fallback
            }
        }

        return ResponseEntity.ok(response); // 200
//        try {
//            ApiResponse<?> response = trainService.addTrain(dto);
//            if (!response.isSuccess()) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//            }
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse<>(false, "Server error: " + e.getMessage(), null));
//        }
    }

    @PutMapping("train/update")
    public ResponseEntity<ApiResponse<?>> updateTrain(@RequestBody TrainRequestDTO dto) {
        ApiResponse<?> response = trainService.updateTrain(dto);

        if (!response.isSuccess()) {
            String message = response.getMessage();
            if ("Train name already in use".equals(message)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409
            } else if ("Train not found".equals(message) || "Route not found".equals(message)
                    || "Train ID is required for update".equals(message)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 fallback
            }
        }

        return ResponseEntity.ok(response); // 200
//        try {
//            ApiResponse<?> response = trainService.updateTrain(dto);
//            if (!response.isSuccess()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//            }
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse<>(false, "Server error: " + e.getMessage(), null));
//        }
    }
}
