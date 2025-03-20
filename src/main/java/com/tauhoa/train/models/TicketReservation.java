package com.tauhoa.train.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_reservation")
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

    @Column(name = "reservation_status", nullable = false, length = 50)
    private String reservationStatus;

    // Constructors
    public TicketReservation() {}

    public TicketReservation(Trip trip, Seat seat, Station departureStation, Station arrivalStation, LocalDateTime holdTime, String reservationStatus) {
        this.trip = trip;
        this.seat = seat;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.holdTime = holdTime;
        this.reservationStatus = reservationStatus;
    }

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public LocalDateTime getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(LocalDateTime holdTime) {
        this.holdTime = holdTime;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public String toString() {
        return "TicketReservation{" +
                "reservationId=" + reservationId +
                ", trip=" + trip +
                ", seat=" + seat +
                ", departureStation=" + departureStation +
                ", arrivalStation=" + arrivalStation +
                ", holdTime=" + holdTime +
                ", reservationStatus='" + reservationStatus + "'" +
                '}';
    }
}
