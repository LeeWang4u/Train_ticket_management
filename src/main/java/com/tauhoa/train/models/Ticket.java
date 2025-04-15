package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "purchase_time", nullable = false)
    private LocalDateTime purchaseTime;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "ticket_status", nullable = false)
    private String ticketStatus;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "departure_station_id", nullable = false)
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name = "arrival_station_id", nullable = false)
    private Station arrivalStation;

    @ManyToOne
    @JoinColumn(name = "reservation_code_id")
    private ReservationCode reservationCode;

    public Ticket( LocalDateTime purchaseTime, BigDecimal price, BigDecimal discount, BigDecimal totalPrice, String ticketStatus, Passenger passenger, Customer customer, ReservationCode reservationCode) {
        this.purchaseTime = purchaseTime;
        this.price = price;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.ticketStatus = ticketStatus;
        this.passenger = passenger;
        this.customer = customer;
        this.reservationCode = reservationCode;
    }
}
