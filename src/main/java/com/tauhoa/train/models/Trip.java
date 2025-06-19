package com.tauhoa.train.models;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<CarriageList> carriageLists = new ArrayList<>();

        @Column(name = "base_price", nullable = false)
        private BigDecimal basePrice;

        @JsonFormat(pattern = "yyyy-MM-dd")
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

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }
}
