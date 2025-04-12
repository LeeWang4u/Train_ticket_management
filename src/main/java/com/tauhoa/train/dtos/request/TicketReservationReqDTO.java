package com.tauhoa.train.dtos.request;

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
public class TicketReservationReqDTO {



        @NotNull(message = "Thông tin ghế không được để trống")
        private int seat;

        @NotNull(message = "Thông tin chuyến đi không được để trống")
        private int trip;

        @NotNull(message = "Ga đến không được để trống")
        private String arrivalStation;

        @NotNull(message = "Ga đi không được để trống")
        private String departureStation;

}
