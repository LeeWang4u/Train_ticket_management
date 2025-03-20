package com.tauhoa.train.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "departure_station_id", nullable = false)
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name = "arrival_station_id", nullable = false)
    private Station arrivalStation;

    @Column(name = "purchase_time", nullable = false)
    private LocalDateTime purchaseTime;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "ticket_status", nullable = false, length = 50)
    private String ticketStatus;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = true)
    private Invoice invoice;

    // Constructors
    public Ticket() {}

    public Ticket(Trip trip, Seat seat, Station departureStation, Station arrivalStation, LocalDateTime purchaseTime, BigDecimal price, BigDecimal discount, BigDecimal totalPrice, String ticketStatus, Passenger passenger, User user, Invoice invoice) {
        this.trip = trip;
        this.seat = seat;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.purchaseTime = purchaseTime;
        this.price = price;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.ticketStatus = ticketStatus;
        this.passenger = passenger;
        this.user = user;
        this.invoice = invoice;
    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", trip=" + trip +
                ", seat=" + seat +
                ", departureStation=" + departureStation +
                ", arrivalStation=" + arrivalStation +
                ", purchaseTime=" + purchaseTime +
                ", price=" + price +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", ticketStatus='" + ticketStatus + "'" +
                ", passenger=" + passenger +
                ", user=" + user +
                ", invoice=" + invoice +
                '}';
    }
}
