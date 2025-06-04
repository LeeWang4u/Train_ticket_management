package com.tauhoa.train.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "carriage_list")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarriageList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carriage_list_id")
    private int carriageListId;

    @ManyToOne
    @JoinColumn(name = "compartment_id", nullable = false)
    private Compartment compartment;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonBackReference
    private Trip trip;

    @OneToMany(mappedBy = "carriageList", fetch = FetchType.LAZY)
    //@JsonManagedReference
    private List<Seat> seats;

    @Column(name = "stt", nullable = false)
    private int stt;

    CarriageList(Compartment compartment, Trip trip, int stt) {
        this.compartment = compartment;
        this.trip = trip;
        this.stt = stt;
    }
}