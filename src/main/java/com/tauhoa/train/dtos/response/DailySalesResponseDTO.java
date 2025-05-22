package com.tauhoa.train.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailySalesResponseDTO {
    private LocalDate date;
    private BigDecimal totalAmount;

    public DailySalesResponseDTO(LocalDate date, BigDecimal totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }
    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}

