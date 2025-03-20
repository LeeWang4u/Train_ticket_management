package com.tauhoa.train.models;

import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "seat_name", nullable = false, length = 50)
    private String seatName;

    @Column(name = "level", nullable = false)
    private int level;

    @ManyToOne
    @JoinColumn(name = "carriage_list_id", nullable = false)
    private CarriageList carriageList;

    @Column(name = "seat_status", nullable = false, length = 50)
    private String seatStatus;

    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    // Constructors
    public Seat() {}

    public Seat(String seatName, int level, CarriageList carriageList, String seatStatus, String seatNumber) {
        this.seatName = seatName;
        this.level = level;
        this.carriageList = carriageList;
        this.seatStatus = seatStatus;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public CarriageList getCarriageList() {
        return carriageList;
    }

    public void setCarriageList(CarriageList carriageList) {
        this.carriageList = carriageList;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // toString Method
    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", seatName='" + seatName + '\'' +
                ", level=" + level +
                ", carriageList=" + carriageList +
                ", seatStatus='" + seatStatus + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                '}';
    }
}

