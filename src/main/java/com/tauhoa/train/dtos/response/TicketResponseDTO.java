package com.tauhoa.train.dtos.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketResponseDTO {
    private int ticketId;
    private int tripId;
    private String departureStation;
    private String arrivalStation;
    private int seatId;
    private String trainName;
    private String routeName;
}
