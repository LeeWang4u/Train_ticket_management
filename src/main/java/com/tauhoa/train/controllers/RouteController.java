package com.tauhoa.train.controllers;

import com.tauhoa.train.dtos.response.RouteResponseDTO;
import com.tauhoa.train.dtos.response.TrainResponseDTO;
import com.tauhoa.train.services.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RouteController {

    private final RouteService routeService;
    @GetMapping("route/all")
    public ResponseEntity<List<RouteResponseDTO>> getAllStation() {
        List<RouteResponseDTO> routeResponseDTOS = routeService.getAllRoute();
        return ResponseEntity.ok(routeResponseDTOS);
    }
}
