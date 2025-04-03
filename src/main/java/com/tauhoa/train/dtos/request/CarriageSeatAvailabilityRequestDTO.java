package com.tauhoa.train.dtos.request;

public class CarriageSeatAvailabilityRequestDTO {
    private int tripId;
    private int carriageListId;
    private int departureStationId;
    private int arrivalStationId;

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

    public int getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(int departureStationId) {
        this.departureStationId = departureStationId;
    }

    public int getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(int arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }
}