package com.tauhoa.train.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ticket_type")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_type_id")
    private int ticketTypeId;

    @Column(name = "ticket_type_name", nullable = false, length = 100)
    private String ticketTypeName;

    @Column(name = "discount_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal discountRate;

    // Constructors
    public TicketType() {}

    public TicketType(String ticketTypeName, BigDecimal discountRate) {
        this.ticketTypeName = ticketTypeName;
        this.discountRate = discountRate;
    }

    // Getters and Setters
    public int getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(int ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    // toString Method
    @Override
    public String toString() {
        return "TicketType{" +
                "ticketTypeId=" + ticketTypeId +
                ", ticketTypeName='" + ticketTypeName + '\'' +
                ", discountRate=" + discountRate +
                '}';
    }
}