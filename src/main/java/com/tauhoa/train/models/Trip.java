package com.tauhoa.train.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int tripId;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;

    @Column(name = "trip_status", nullable = false, length = 50)
    private String tripStatus;

    // Constructors
    public Trip() {}

    public Trip(Train train, LocalDate tripDate, String tripStatus) {
        this.train = train;
        this.tripDate = tripDate;
        this.tripStatus = tripStatus;
    }

    // Getters and Setters
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    // toString Method
    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", train=" + train +
                ", tripDate=" + tripDate +
                ", tripStatus='" + tripStatus + '\'' +
                '}';
    }
}
