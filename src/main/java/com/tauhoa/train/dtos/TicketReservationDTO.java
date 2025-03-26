package com.tauhoa.train.dtos;

import com.tauhoa.train.models.Seat;
import com.tauhoa.train.models.Station;
import com.tauhoa.train.models.Trip;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketReservationDTO {
    @NotNull(message = "Thông tin ghế không được để trống")
    private Seat seat;

    @NotNull(message = "Thông tin chuyến đi không được để trống")
    private Trip trip;

    @NotNull(message = "Ga đến không được để trống")
    private Station arrivalStationId;

    @NotNull(message = "Ga đi không được để trống")
    private Station departureStationId;

    @NotNull(message = "Trạng thái không được để trống")
    private String reservationStatus;
}
