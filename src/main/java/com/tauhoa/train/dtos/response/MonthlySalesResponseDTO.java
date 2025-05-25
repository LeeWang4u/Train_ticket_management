package com.tauhoa.train.dtos.response;
import java.math.BigDecimal;

public class MonthlySalesResponseDTO {
    private String month;
    private BigDecimal total;

    public MonthlySalesResponseDTO(Integer year, Integer month, BigDecimal total) {
        this.month = String.format("%04d-%02d", year, month);
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
