package com.tauhoa.train.services;

import com.tauhoa.train.dtos.response.RouteResponseDTO;
import com.tauhoa.train.dtos.response.TrainResponseDTO;
import com.tauhoa.train.models.Route;
import com.tauhoa.train.models.Train;
import com.tauhoa.train.repositories.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService implements IRouteService {
    private final RouteRepository routeRepository;

    @Override
    public List<RouteResponseDTO> getAllRoute() {
        List<Route> routes =   routeRepository.findAll();

        return routes.stream()
                .map(route -> new RouteResponseDTO(
                        route.getRouteId(),
                        route.getRouteName())) // Ánh xạ các trường tương ứng
                .collect(Collectors.toList());
    }
}
