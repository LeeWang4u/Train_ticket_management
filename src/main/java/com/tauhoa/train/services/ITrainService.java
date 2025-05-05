package com.tauhoa.train.services;

import com.tauhoa.train.common.ApiResponse;
import com.tauhoa.train.dtos.request.AddTrainRequestDTO;
import com.tauhoa.train.dtos.request.TrainRequestDTO;
import com.tauhoa.train.dtos.response.TrainResponseDTO;
import com.tauhoa.train.models.Train;

import java.util.List;

public interface ITrainService {

    List<TrainResponseDTO> getAllTrain();

    ApiResponse<Train> addTrain(AddTrainRequestDTO addTrainRequestDTO);

    ApiResponse<Train> updateTrain(TrainRequestDTO updateTrainRequestDTO);
}
