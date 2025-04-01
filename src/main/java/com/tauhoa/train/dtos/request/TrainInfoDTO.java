package com.tauhoa.train.dtos.request;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainInfoDTO {

    private String trainCode; // Mã tàu (SE8, SE6, ...)
    private LocalTime departureTime; // TG đi
    private LocalTime arrivalTime; // TG đến
    private int bookedSeats; // SL chỗ đặt
    private int availableSeats; // SL chỗ trống
    private Map<String, Integer> compartmentAvailableSeats;
}
