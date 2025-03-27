package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservationId;

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

    @Column(name = "hold_time", nullable = false)
    private LocalDateTime holdTime;

    @Column(name = "reservation_status", nullable = false)
    private String reservationStatus;

    public TicketReservation(Trip trip, Seat seat, Station departureStation, Station arrivalStation, LocalDateTime holdTime, String reservationStatus) {
        this.trip = trip;
        this.seat = seat;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.holdTime = holdTime;
        this.reservationStatus = reservationStatus;
    }
}
