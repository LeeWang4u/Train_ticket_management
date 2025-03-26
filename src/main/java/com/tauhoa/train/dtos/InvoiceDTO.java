package com.tauhoa.train.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private LocalDateTime createdTime;

    private String invoiceStatus;
    @Min(value = 0, message = "Tổng tiền phải lớn hơn hoặc bằng 0")
    private BigDecimal totalPrice;
}
