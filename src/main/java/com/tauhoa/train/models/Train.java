package com.tauhoa.train.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private int trainId;

    @Column(name = "train_name", nullable = false, length = 255)
    private String trainName;

    @Column(name = "listed_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal listedPrice;

    @Column(name = "train_type", nullable = false, length = 50)
    private String trainType;

    @Column(name = "train_status", nullable = false, length = 50)
    private String trainStatus;

    @Column(name = "carriage_number", nullable = false)
    private int carriageNumber;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    // Constructors
    public Train() {}

    public Train(String trainName, BigDecimal listedPrice, String trainType, String trainStatus, int carriageNumber, Route route, LocalTime departureTime) {
        this.trainName = trainName;
        this.listedPrice = listedPrice;
        this.trainType = trainType;
        this.trainStatus = trainStatus;
        this.carriageNumber = carriageNumber;
        this.route = route;
        this.departureTime = departureTime;
    }

    // Getters and Setters
    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public BigDecimal getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(BigDecimal listedPrice) {
        this.listedPrice = listedPrice;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getTrainStatus() {
        return trainStatus;
    }

    public void setTrainStatus(String trainStatus) {
        this.trainStatus = trainStatus;
    }

    public int getCarriageNumber() {
        return carriageNumber;
    }

    public void setCarriageNumber(int carriageNumber) {
        this.carriageNumber = carriageNumber;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    // toString Method
    @Override
    public String toString() {
        return "Train{" +
                "trainId=" + trainId +
                ", trainName='" + trainName + '\'' +
                ", listedPrice=" + listedPrice +
                ", trainType='" + trainType + '\'' +
                ", trainStatus='" + trainStatus + '\'' +
                ", carriageNumber=" + carriageNumber +
                ", route=" + route +
                ", departureTime=" + departureTime +
                '}';
    }
}
