package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "station")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private int stationId;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    @Column(name = "location", nullable = false)
    private String location;
    public Station(String stationName, String location) {
        this.stationName = stationName;
        this.location = location;
    }
}
