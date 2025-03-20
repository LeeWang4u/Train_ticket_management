package com.tauhoa.train.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "route_detail")
public class RouteDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_detail_id")
    private int routeDetailId;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "ordinal_number", nullable = false)
    private int ordinalNumber;

    @Column(name = "distance", nullable = false, precision = 10, scale = 2)
    private BigDecimal   distance;

    // Constructors
    public RouteDetail() {}

    public RouteDetail(Station station, Route route, int ordinalNumber, BigDecimal distance) {
        this.station = station;
        this.route = route;
        this.ordinalNumber = ordinalNumber;
        this.distance = distance;
    }

    // Getters and Setters
    public int getRouteDetailId() {
        return routeDetailId;
    }

    public void setRouteDetailId(int routeDetailId) {
        this.routeDetailId = routeDetailId;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    // toString Method
    @Override
    public String toString() {
        return "RouteDetail{" +
                "routeDetailId=" + routeDetailId +
                ", station=" + station +
                ", route=" + route +
                ", ordinalNumber=" + ordinalNumber +
                ", distance=" + distance +
                '}';
    }
}