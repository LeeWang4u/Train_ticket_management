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

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private TicketReservation reservation;

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
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public Ticket(TicketReservation reservation, LocalDateTime purchaseTime, BigDecimal price, BigDecimal discount, BigDecimal totalPrice, String ticketStatus, Passenger passenger, Customer customer, Invoice invoice) {
        this.reservation = reservation;
        this.purchaseTime = purchaseTime;
        this.price = price;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.ticketStatus = ticketStatus;
        this.passenger = passenger;
        this.customer = customer;
        this.invoice = invoice;
    }
}
