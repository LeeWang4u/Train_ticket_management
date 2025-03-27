package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "passenger")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private int passengerId;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "cccd", nullable = false)
    private String cccd;

    @Column(name = "dob", nullable = false)
    private LocalDateTime dob;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    public Passenger(String fullname, String cccd, LocalDateTime dob, TicketType ticketType) {
        this.fullname = fullname;
        this.cccd = cccd;
        this.dob = dob;
        this.ticketType = ticketType;
    }
}
