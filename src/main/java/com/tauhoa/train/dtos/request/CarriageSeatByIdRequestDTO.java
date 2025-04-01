package com.tauhoa.train.dtos.request;

public class CarriageSeatByIdRequestDTO {
    private int tripId;
    private int carriageListId;

    // Getter và Setter
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getCarriageListId() {
        return carriageListId;
    }

    public void setCarriageListId(int carriageListId) {
        this.carriageListId = carriageListId;
    }
}