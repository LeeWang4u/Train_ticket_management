package com.tauhoa.train.models;

import jakarta.persistence.*;

@Entity
@Table(name = "compartment")
public class Compartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compartment_id")
    private int compartmentId;

    @Column(name = "compartment_type", nullable = false, length = 50)
    private String compartmentType;

    @Column(name = "compartment_name", nullable = false, length = 255)
    private String compartmentName;

    @Column(name = "seat_count", nullable = false)
    private int seatCount;

    // Constructors
    public Compartment() {}

    public Compartment(String compartmentType, String compartmentName, int seatCount) {
        this.compartmentType = compartmentType;
        this.compartmentName = compartmentName;
        this.seatCount = seatCount;
    }

    // Getters and Setters
    public int getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(int compartmentId) {
        this.compartmentId = compartmentId;
    }

    public String getCompartmentType() {
        return compartmentType;
    }

    public void setCompartmentType(String compartmentType) {
        this.compartmentType = compartmentType;
    }

    public String getCompartmentName() {
        return compartmentName;
    }

    public void setCompartmentName(String compartmentName) {
        this.compartmentName = compartmentName;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    // toString Method
    @Override
    public String toString() {
        return "Compartment{" +
                "compartmentId=" + compartmentId +
                ", compartmentType='" + compartmentType + '\'' +
                ", compartmentName='" + compartmentName + '\'' +
                ", seatCount=" + seatCount +
                '}';
    }
}