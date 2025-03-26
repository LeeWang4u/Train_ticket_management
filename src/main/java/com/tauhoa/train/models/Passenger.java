package com.tauhoa.train.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private int passengerId;

    @Column(name = "fullname", nullable = false, length = 255)
    private String fullname;

    @Column(name = "cccd", nullable = false, unique = true, length = 20)
    private String cccd;

    @Column(name = "dob", nullable = false)
    private LocalDateTime dob;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    // Constructors
    public Passenger() {}

    public Passenger(String fullname, String cccd, LocalDateTime dob, TicketType ticketType) {
        this.fullname = fullname;
        this.cccd = cccd;
        this.dob = dob;
        this.ticketType = ticketType;
    }

    // Getters and Setters
    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    // toString Method
    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", fullname='" + fullname + '\'' +
                ", cccd='" + cccd + '\'' +
                ", dob=" + dob +
                ", ticketType=" + ticketType +
                '}';
    }
}
