package com.tauhoa.train.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnoreProperties("train")
    @OneToMany(mappedBy = "train", fetch = FetchType.LAZY)
    private List<TrainSchedule> trainSchedules;

    public Train(String trainName, Route route) {
        this.trainName = trainName;
        this.route = route;
    }
}
