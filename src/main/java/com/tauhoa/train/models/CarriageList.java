package com.tauhoa.train.models;

import jakarta.persistence.*;

@Entity
@Table(name = "carriage_list")
public class CarriageList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carriage_list_id")
    private int carriageListId;

    @ManyToOne
    @JoinColumn(name = "compartment_id", nullable = false)
    private Compartment compartment;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    // Constructors
    public CarriageList() {}

    public CarriageList(Compartment compartment, Trip trip) {
        this.compartment = compartment;
        this.trip = trip;
    }

    // Getters and Setters
    public int getCarriageListId() {
        return carriageListId;
    }

    public void setCarriageListId(int carriageListId) {
        this.carriageListId = carriageListId;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    // toString Method
    @Override
    public String toString() {
        return "CarriageList{" +
                "carriageListId=" + carriageListId +
                ", compartment=" + compartment +
                ", trip=" + trip +
                '}';
    }
}
