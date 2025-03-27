package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "compartment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compartment_id")
    private int compartmentId;

    @Column(name = "compartment_name", nullable = false)
    private String compartmentName;

    @Column(name = "class_factor", nullable = false)
    private BigDecimal classFactor;

    @Column(name = "seat_count", nullable = false)
    private int seatCount;

    Compartment(String compartmentName, BigDecimal classFactor, int seatCount) {
        this.compartmentName = compartmentName;
        this.classFactor = classFactor;
        this.seatCount = seatCount;
    }
}
