package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String email);
    List<Customer> findByCccdAndPhone(String cccd, String phone);
}
