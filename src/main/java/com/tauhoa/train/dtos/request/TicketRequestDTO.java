package com.tauhoa.train.dtos.request;

import com.tauhoa.train.models.TicketType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequestDTO {
    private int ticketId;

    private BigDecimal discount;

    @NotBlank(message = "Tên hành khách không được để trống")
    private String fullName;

    @NotBlank(message = "CCCD không được để trống")
    @Pattern(regexp = "\\d{9,12}", message = "CCCD phải có 9 đến 12 chữ số")
    private String cccd;

    @NotNull(message = "Giá vé không được để trống")
    @DecimalMin(value = "0.01", message = "Giá vé phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Tổng giá không được để trống")
    @DecimalMin(value = "0.01", message = "Tổng giá phải lớn hơn 0")
    private BigDecimal totalPrice;

    @NotNull(message = "Thông tin ghế không được để trống")
    private int seat;

    @NotNull(message = "Thông tin chuyến đi không được để trống")
    private int trip;

    @NotNull(message = "Ga đến không được để trống")
    private String arrivalStation;

    @NotNull(message = "Ga đi không được để trống")
    private String departureStation;

    @NotNull(message = "Loại vé không được để trống")
    private TicketType ticketType;
}
