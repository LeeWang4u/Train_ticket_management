package com.tauhoa.train.dtos.request;

public class CarriageSeatRequestDTO {
    private int tripId;
    private int stt;

    // Getter và Setter
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
}