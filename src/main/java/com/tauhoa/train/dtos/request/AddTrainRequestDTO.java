package com.tauhoa.train.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddTrainRequestDTO {
    private String trainName;
    private String route;

}
