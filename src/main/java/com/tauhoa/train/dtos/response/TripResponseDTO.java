package com.tauhoa.train.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TripResponseDTO {
    private int tripId;
    private String trainName;
    private BigDecimal basePrice;
    private LocalDateTime tripDate;
    private String tripStatus;
    private String departureStation;
    private String arrivalStation;
    private LocalDateTime departureTimeAtStation;

    // Constructor
    public TripResponseDTO(int tripId, String trainName, BigDecimal basePrice,
                           LocalDateTime tripDate, String tripStatus,
                           String departureStation, String arrivalStation,
                           LocalDateTime departureTimeAtStation) {
        this.tripId = tripId;
        this.trainName = trainName;
        this.basePrice = basePrice;
        this.tripDate = tripDate;
        this.tripStatus = tripStatus;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTimeAtStation = departureTimeAtStation;
    }

    // Getter và Setter
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDateTime getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDateTime tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public LocalDateTime getDepartureTimeAtStation() {
        return departureTimeAtStation;
    }

    public void setDepartureTimeAtStation(LocalDateTime departureTimeAtStation) {
        this.departureTimeAtStation = departureTimeAtStation;
    }
}