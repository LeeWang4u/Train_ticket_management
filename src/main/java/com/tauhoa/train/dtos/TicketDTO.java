package com.tauhoa.train.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tauhoa.train.models.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {



    private LocalDateTime purchaseTime;

    @NotNull(message = "Giá vé không được để trống")
    @Min(value = 0, message = "Giá vé phải lớn hơn hoặc bằng 0")
    private BigDecimal price;

    @Min(value = 0, message = "Giảm giá phải lớn hơn hoặc bằng 0")
    private BigDecimal discount;

    @Min(value = 0, message = "Tổng tiền phải lớn hơn hoặc bằng 0")
    private BigDecimal totalPrice;

    @NotNull(message = "Trạng thái vé không được để trống")
    @Size(max = 10, message = "Trạng thái vé không được dài quá 10 ký tự")
    private String ticketStatus;

    Passenger passengerId;

    User userId;

    Invoice invoiceId; // Có thể null nếu chưa có hóa đơn

    Trip trip;

    Seat seat;

    Station departureStation;

    Station arrivalStation;
}
