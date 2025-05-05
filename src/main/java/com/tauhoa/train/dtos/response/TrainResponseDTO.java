package com.tauhoa.train.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrainResponseDTO {

    private int trainId;

    private String trainName;

    private String route;

    public TrainResponseDTO(int trainId, String trainName, String route) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.route = route;
    }
}
