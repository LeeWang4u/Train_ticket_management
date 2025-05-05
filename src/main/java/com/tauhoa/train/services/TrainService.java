package com.tauhoa.train.services;

import com.tauhoa.train.dtos.response.TrainResponseDTO;
import com.tauhoa.train.models.Train;
import com.tauhoa.train.repositories.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainService implements ITrainService {
    private final TrainRepository trainRepository;
    @Override
    public List<TrainResponseDTO> getAllTrain() {
        List<Train> trains =   trainRepository.findAll();

        return trains.stream()
                .map(train -> new TrainResponseDTO(
                        train.getTrainId(),
                        train.getTrainName(),
                        train.getRoute().getRouteName())) // Ánh xạ các trường tương ứng
                .collect(Collectors.toList());

    }
}
