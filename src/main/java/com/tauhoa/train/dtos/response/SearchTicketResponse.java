package com.tauhoa.train.dtos.response;

import com.tauhoa.train.models.Ticket;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SearchTicketResponse {
    private int ticketId;
    private String passengerName;
    private String departureStation;
    private String arrivalStation;
    private String seatName;
    private String trainName;
    
}
