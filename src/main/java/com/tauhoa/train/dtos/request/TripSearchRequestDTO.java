package com.tauhoa.train.dtos.request;

import java.time.LocalDate;

public class TripSearchRequestDTO {
    private String departureStation;
    private String arrivalStation;
    private LocalDate tripDate;

    // Getter và Setter
    public String getDepartureStation() {
        return departureStation;
    }

    public TripSearchRequestDTO(String departureStation, String arrivalStation, LocalDate tripDate) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.tripDate = tripDate;
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

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }
}