package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.TripSearchRequestDTO;
import com.tauhoa.train.dtos.response.TrainResponseDTO;
import com.tauhoa.train.dtos.response.TripResponseDTO;

import java.util.List;

public interface ITrainService {

    List<TrainResponseDTO> getAllTrain();
}
