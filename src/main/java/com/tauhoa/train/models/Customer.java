package com.tauhoa.train.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "cccd", nullable = false)
    private String cccd;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    public Customer(String email, String phone, String cccd, String fullname) {
        this.email = email;
        this.phone = phone;
        this.cccd = cccd;
        this.fullname = fullname;
    }
}
