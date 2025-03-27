package com.tauhoa.train.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "seat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "seat_factor", nullable = false)
    private BigDecimal seatFactor;

    @ManyToOne
    @JoinColumn(name = "carriage_list_id", nullable = false)
    private CarriageList carriageList;

    @Column(name = "seat_status", nullable = false)
    private String seatStatus;

    public Seat(String seatNumber, Integer floor, BigDecimal seatFactor, String seatStatus, CarriageList carriageList) {
        this.seatNumber = seatNumber;
        this.floor = floor;
        this.seatFactor = seatFactor;
        this.seatStatus = seatStatus;
        this.carriageList = carriageList;
    }
}
