package com.tauhoa.train.services;

import com.tauhoa.train.dtos.CustomerDTO;
import com.tauhoa.train.models.Customer;
import com.tauhoa.train.models.User;

public interface ICustomerService {
    Customer save(CustomerDTO customerDTO);
//    void findByEmail(String email);
}
