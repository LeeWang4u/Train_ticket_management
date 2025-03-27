package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ticket_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_type_id")
    private int ticketTypeId;

    @Column(name = "ticket_type_name", nullable = false)
    private String ticketTypeName;

    @Column(name = "discount_rate", nullable = false)
    private BigDecimal discountRate;

    public TicketType(String ticketTypeName, BigDecimal discountRate) {
        this.ticketTypeName = ticketTypeName;
        this.discountRate = discountRate;
    }
}
