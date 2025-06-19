package com.tauhoa.train.dtos.response;

import com.tauhoa.train.models.Ticket;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {
    private int ticketId;
    private int tripId;
    private String departureStation;
    private String arrivalStation;
    private int seatId;
    private String trainName;
    private String routeName;

    public static TicketResponseDTO toTicketResponseDTO(Ticket ticket) {
        return TicketResponseDTO.builder()
                .ticketId(ticket.getTicketId())
                .tripId(ticket.getTrip().getTripId())
                .departureStation(ticket.getDepartureStation().getStationName())
                .arrivalStation(ticket.getArrivalStation().getStationName())
                .seatId(ticket.getSeat().getSeatId())
                .trainName(ticket.getTrip().getTrain().getTrainName())
                .routeName(ticket.getTrip().getTrain().getRoute().getRouteName())
                .build();
    }
}
