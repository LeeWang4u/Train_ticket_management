package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.CustomerDTO;
import com.tauhoa.train.models.Customer;

public interface ICustomerService {
    Customer save(CustomerDTO customerDTO);
//    void findByEmail(String email);
}
