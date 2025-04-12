package com.tauhoa.train.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int tripId;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;

    @Column(name = "trip_status", nullable = false)
    private String tripStatus;

    public Trip(Train train, BigDecimal basePrice, LocalDate tripDate, String tripStatus) {
        this.train = train;
        this.basePrice = basePrice;
        this.tripDate = tripDate;
        this.tripStatus = tripStatus;
    }
}
