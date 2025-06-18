package com.tauhoa.train.dtos.request;

public class TripSeatAvailabilityRequestDTO {
    private int tripId;
    private int departureStationId;
    private int arrivalStationId;

    public TripSeatAvailabilityRequestDTO(int tripId, int departureStationId, int arrivalStationId) {
        this.tripId = tripId;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
    }

    public TripSeatAvailabilityRequestDTO() {
        // Constructor rỗng để Jackson có thể khởi tạo đối tượng
    }

    // Getter và Setter
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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