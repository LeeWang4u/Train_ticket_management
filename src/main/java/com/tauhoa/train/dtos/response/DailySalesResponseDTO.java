package com.tauhoa.train.dtos.response;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class DailySalesResponseDTO {
    @Getter
    private Date date;
    private BigDecimal totalSales;

    public DailySalesResponseDTO(Date date, BigDecimal totalSales) {
        this.date = date;
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }
}

