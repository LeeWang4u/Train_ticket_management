package com.tauhoa.train.services;

import com.tauhoa.train.common.ApiResponse;
import com.tauhoa.train.dtos.request.AddTrainRequestDTO;
import com.tauhoa.train.dtos.request.TrainRequestDTO;
import com.tauhoa.train.dtos.response.TrainResponseDTO;
import com.tauhoa.train.models.Route;
import com.tauhoa.train.models.Train;
import com.tauhoa.train.repositories.RouteRepository;
import com.tauhoa.train.repositories.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainService implements ITrainService {
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
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

    public ApiResponse<Train> addTrain(AddTrainRequestDTO dto) {
        if (trainRepository.existsByTrainName(dto.getTrainName())) {
            return new ApiResponse<>(false, "Train name already exists", null);
        }

        Optional<Route> routeOpt = routeRepository.findRouteByRouteName(dto.getRoute());
        if (routeOpt.isEmpty()) {
            return new ApiResponse<>(false, "Route not found", null);
        }

        Train train = new Train();
        train.setTrainName(dto.getTrainName());
        train.setRoute(routeOpt.get());

        Train savedTrain = trainRepository.save(train);
        return new ApiResponse<>(true, "Train created successfully", savedTrain);
    }

    public ApiResponse<Train> updateTrain(TrainRequestDTO dto) {
        if (dto.getTrainId() == null) {
            return new ApiResponse<>(false, "Train ID is required for update", null);
        }

        Optional<Train> existingTrainOpt = trainRepository.findById(dto.getTrainId());
        if (existingTrainOpt.isEmpty()) {
            return new ApiResponse<>(false, "Train not found", null);
        }

        // Kiểm tra trainName có bị trùng không (khác chính bản thân nó)
        Optional<Train> otherTrainWithSameName = trainRepository.findByTrainName(dto.getTrainName());
        if (otherTrainWithSameName.isPresent()
                && otherTrainWithSameName.get().getTrainId() != dto.getTrainId()) {
            return new ApiResponse<>(false, "Train name already in use", null); // dùng cho 409
        }

        Optional<Route> routeOpt = routeRepository.findRouteByRouteName(dto.getRoute());
        if (routeOpt.isEmpty()) {
            return new ApiResponse<>(false, "Route not found", null);
        }

        Train existingTrain = existingTrainOpt.get();
        existingTrain.setTrainName(dto.getTrainName());
        existingTrain.setRoute(routeOpt.get());

        Train updatedTrain = trainRepository.save(existingTrain);
        return new ApiResponse<>(true, "Train updated successfully", updatedTrain);
//        if (dto.getTrainId() == null) {
//            return new ApiResponse<>(false, "Train ID is required for update", null);
//        }
//
//        Optional<Train> existingTrainOpt = trainRepository.findById(dto.getTrainId());
//        if (existingTrainOpt.isEmpty()) {
//            return new ApiResponse<>(false, "Train not found", null);
//        }
//
//        Train existingTrain = existingTrainOpt.get();
//
//        // Nếu đổi trainName thì phải kiểm tra xem tên mới đã tồn tại chưa
////        Optional<Train> otherTrainWithSameName = trainRepository.findByTrainName(dto.getTrainName());
////        if (otherTrainWithSameName.isPresent() &&
////                !otherTrainWithSameName.get().getTrainId().equals(dto.getTrainId())) {
////            return new ApiResponse<>(false, "Train name already in use", null);
////        }
//
//        Optional<Train> otherTrainWithSameName = trainRepository.findByTrainName(dto.getTrainName());
//        if (otherTrainWithSameName.isPresent() &&
//                otherTrainWithSameName.get().getTrainId() != dto.getTrainId()) {
//            return new ApiResponse<>(false, "Train name already in use", null);
//        }
//
//
//        Optional<Route> routeOpt = routeRepository.findRouteByRouteName(dto.getRoute());
//        if (routeOpt.isEmpty()) {
//            return new ApiResponse<>(false, "Route not found", null);
//        }
//
//        existingTrain.setTrainName(dto.getTrainName());
//        existingTrain.setRoute(routeOpt.get());
//
//        Train updatedTrain = trainRepository.save(existingTrain);
//        return new ApiResponse<>(true, "Train updated successfully", updatedTrain);
    }


}
