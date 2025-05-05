package com.tauhoa.train.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrainRequestDTO {
    private Integer trainId;
    private String trainName;
    private String route;

    public TrainRequestDTO(Integer trainId, String trainName, String route) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.route = route;
    }
}
