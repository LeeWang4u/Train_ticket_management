package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "train_schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_schedule_id")
    private int trainScheduleId;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "ordinal_number", nullable = false)
    private int ordinalNumber;

    @Column(name = "distance", nullable = false)
    private BigDecimal distance;

    public TrainSchedule(Station station, Train train, LocalTime arrivalTime, LocalTime departureTime, Integer ordinalNumber, BigDecimal distance) {
        this.station = station;
        this.train = train;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.ordinalNumber = ordinalNumber;
        this.distance = distance;
    }
}
