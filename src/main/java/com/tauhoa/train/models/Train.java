package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "train")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private int trainId;

    @Column(name = "train_name", nullable = false)
    private String trainName;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    public Train(String trainName, Route route) {
        this.trainName = trainName;
        this.route = route;
    }
}
