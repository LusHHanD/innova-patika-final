package com.innova.project.patikafinal.service.impl;

import com.innova.project.patikafinal.Exception.CustomerNotFoundException;
import com.innova.project.patikafinal.dto.CustomerDTO;
import com.innova.project.patikafinal.entity.Customer;
import com.innova.project.patikafinal.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;


    private CustomerServiceImpl customerService;

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(customerRepository, new ModelMapper());
    }

    @Test
    void deleteById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void getById() {
        Long id = 12345678901L;
        Customer customer = new Customer(id, "Test 1", "Test 1", 4000D, "5551234455", new ArrayList<>());

        when(customerRepository.findById(id)).thenReturn(java.util.Optional.of(customer));

        CustomerDTO customerDTO = customerService.getById(id);

        assertEquals(customerDTO.getName(), customer.getName());
        assertEquals(customerDTO.getSurname(), customer.getSurname());
        assertEquals(customerDTO.getIncome(), customer.getIncome());
        assertEquals(customerDTO.getIdentityNumber(), customer.getIdentityNumber());
        assertEquals(customerDTO.getPhoneNumber(), customer.getPhoneNumber());
    }

    @Test
    void getAll() {
        List<Customer> customerList = Arrays.asList(
                new Customer(12345678901L, "Test 1", "Test 1", 4000D, "5551234455", new ArrayList<>()),
                new Customer(12345678902L, "Test 2", "Test 2", 5000D, "5551234456", new ArrayList<>())
        );

        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDTO> all = customerService.getAll();
        customerList.forEach(customer -> {
            CustomerDTO actual = all.stream().filter(customerDTO -> customerDTO.getIdentityNumber().equals(customer.getIdentityNumber()))
                    .findFirst().get();

            assertEquals(customer.getName(), actual.getName());
            assertEquals(customer.getSurname(), actual.getSurname());
            assertEquals(customer.getPhoneNumber(), actual.getPhoneNumber());
            assertEquals(customer.getIncome(), actual.getIncome());
        });
    }

    @Test
    void customerNotFound() {
        Long id = 12345678901L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getById(id));
    }
}
