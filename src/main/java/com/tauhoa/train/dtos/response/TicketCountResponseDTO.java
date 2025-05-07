package com.tauhoa.train.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketCountResponseDTO {
    private String departureStation;
    private String arrivalStation;
    private Long ticketCount;
}
