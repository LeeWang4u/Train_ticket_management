package com.tauhoa.train.dtos.request;


import jakarta.validation.constraints.NotNull;
import com.tauhoa.train.validators.ValidDateRange;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidDateRange(start = "startDate", end = "endDate", message = "End date must be after or equal to start date.")
    public class TicketDateRangeRequestDTO {
    @NotNull(message = "Start date is required")
    private String startDate;

    @NotNull(message = "End date is required")
    private String endDate;

    private String type = "raw";
}
