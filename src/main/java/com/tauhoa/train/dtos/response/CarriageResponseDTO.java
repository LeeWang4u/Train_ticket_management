package com.tauhoa.train.dtos.response;


import java.math.BigDecimal;
import java.util.List;

public class CarriageResponseDTO {
    private int carriageListId;
    private int stt;
    private String compartmentName;
    private BigDecimal classFactor;
    private int seatCount;
    private List<SeatResponseDTO> seats;

    public CarriageResponseDTO(int carriageListId, int stt, String compartmentName,
                               BigDecimal classFactor, int seatCount, List<SeatResponseDTO> seats) {
        this.carriageListId = carriageListId;
        this.stt = stt;
        this.compartmentName = compartmentName;
        this.classFactor = classFactor;
        this.seatCount = seatCount;
        this.seats = seats;
    }

    // Getter và Setter
    public int getCarriageListId() {
        return carriageListId;
    }

    public void setCarriageListId(int carriageListId) {
        this.carriageListId = carriageListId;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getCompartmentName() {
        return compartmentName;
    }

    public void setCompartmentName(String compartmentName) {
        this.compartmentName = compartmentName;
    }

    public BigDecimal getClassFactor() {
        return classFactor;
    }

    public void setClassFactor(BigDecimal classFactor) {
        this.classFactor = classFactor;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public List<SeatResponseDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatResponseDTO> seats) {
        this.seats = seats;
    }
}