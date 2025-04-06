package com.tauhoa.train.dtos.request;


import java.math.BigDecimal;
import java.time.LocalDate;

public class AddTripRequestDTO {
    private int trainId;
    private BigDecimal basePrice;
    private LocalDate tripDate;
    private int numSoftSeatCarriages;
    private int numSixBerthCarriages;
    private int numFourBerthCarriages;

    // Getter và Setter
    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public int getNumSoftSeatCarriages() {
        return numSoftSeatCarriages;
    }

    public void setNumSoftSeatCarriages(int numSoftSeatCarriages) {
        this.numSoftSeatCarriages = numSoftSeatCarriages;
    }

    public int getNumSixBerthCarriages() {
        return numSixBerthCarriages;
    }

    public void setNumSixBerthCarriages(int numSixBerthCarriages) {
        this.numSixBerthCarriages = numSixBerthCarriages;
    }

    public int getNumFourBerthCarriages() {
        return numFourBerthCarriages;
    }

    public void setNumFourBerthCarriages(int numFourBerthCarriages) {
        this.numFourBerthCarriages = numFourBerthCarriages;
    }
}