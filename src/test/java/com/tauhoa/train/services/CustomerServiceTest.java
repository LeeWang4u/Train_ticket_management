package com.tauhoa.train.services;


import com.tauhoa.train.dtos.request.CustomerDTO;
import com.tauhoa.train.models.Customer;
import com.tauhoa.train.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void save_whenValidCustomerDTO_shouldReturnSavedCustomer() {
        // Arrange
        CustomerDTO dto = new CustomerDTO();
        dto.setCccd("012345678901");
        dto.setEmail("test@example.com");
        dto.setFullName("Nguyen Van A");
        dto.setPhone("0912345678");

        Customer expectedCustomer = new Customer(
                dto.getEmail(),
                dto.getPhone(),
                dto.getCccd(),
                dto.getFullName()
        );

        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        // Act
        Customer result = customerService.save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getFullName(), result.getFullname());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getPhone(), result.getPhone());
        assertEquals(dto.getCccd(), result.getCccd());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
