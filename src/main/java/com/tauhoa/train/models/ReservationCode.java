package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_code")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_code_id")
    private int reservation_code_;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;


    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    public ReservationCode(BigDecimal totalAmount,  LocalDateTime createdAt) {
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }
}
