package com.tauhoa.train.repositories;

import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String email);

}
