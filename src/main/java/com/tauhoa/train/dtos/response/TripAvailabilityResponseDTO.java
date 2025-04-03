package com.tauhoa.train.dtos.response;

import java.util.List;

public class TripAvailabilityResponseDTO {
    private int tripId;
    private List<CarriageAvailabilityResponseDTO> carriages;

    public TripAvailabilityResponseDTO(int tripId, List<CarriageAvailabilityResponseDTO> carriages) {
        this.tripId = tripId;
        this.carriages = carriages;
    }

    // Getter và Setter
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public List<CarriageAvailabilityResponseDTO> getCarriages() {
        return carriages;
    }

    public void setCarriages(List<CarriageAvailabilityResponseDTO> carriages) {
        this.carriages = carriages;
    }
}