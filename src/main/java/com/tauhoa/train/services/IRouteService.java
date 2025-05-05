package com.tauhoa.train.services;

import com.tauhoa.train.dtos.response.RouteResponseDTO;
import com.tauhoa.train.dtos.response.TrainResponseDTO;

import java.util.List;

public interface IRouteService {

    List<RouteResponseDTO> getAllRoute();
}
