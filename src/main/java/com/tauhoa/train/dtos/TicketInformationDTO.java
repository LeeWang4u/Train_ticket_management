package com.tauhoa.train.dtos;

import com.tauhoa.train.models.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketInformationDTO {
    //tạo dto cho cái này rồi thêm nó vào ticketdto rồi làm tiêp controller ở là được
    // có hết tất cả mọi thứ từ fe gửi về be


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



    @NotNull(message = "Loại vé không được để trống")
    private TicketReservation ticketReservation;

    @NotNull(message = "Loại vé không được để trống")
    private TicketType ticketType;
}
