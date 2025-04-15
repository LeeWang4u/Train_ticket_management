package com.tauhoa.train.services;

import com.tauhoa.train.dtos.request.CustomerDTO;
import com.tauhoa.train.models.Customer;
import com.tauhoa.train.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Customer save(CustomerDTO customerDTO){
        Customer customer = new Customer(customerDTO.getEmail(), customerDTO.getPhone(), customerDTO.getCccd(), customerDTO.getFullName());
        return customerRepository.save(customer);
    }
}
