package com.innova.project.patikafinal.service.impl;

import com.innova.project.patikafinal.Exception.CustomerNotFoundException;
import com.innova.project.patikafinal.dto.CustomerDTO;
import com.innova.project.patikafinal.entity.Customer;
import com.innova.project.patikafinal.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;


    private CustomerServiceImpl customerService;

    private final Long QUERY_ID = 12345678901L;

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(customerRepository, new ModelMapper());
    }

    @Test
    void deleteById() {
        customerService.deleteById(QUERY_ID);
        verify(customerRepository).deleteById(Mockito.any(Long.class));
    }

    @Test
    void save() {
        Customer newCustomer = Customer.builder()
                .identityNumber(QUERY_ID)
                .income(1000D)
                .phoneNumber("5555555555")
                .name("Test 1")
                .surname("Test 1")
                .build();

        CustomerDTO customerDTO = customerService.toDto(newCustomer);

        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(newCustomer);

        CustomerDTO savedData = customerService.save(customerDTO);

        assertEquals(savedData.getIdentityNumber(), newCustomer.getIdentityNumber());
        assertEquals(savedData.getIncome(), newCustomer.getIncome());
        assertEquals(savedData.getName(), newCustomer.getName());
        assertEquals(savedData.getSurname(), newCustomer.getSurname());
        assertEquals(savedData.getPhoneNumber(), newCustomer.getPhoneNumber());
    }

    @Test
    void update() {
        Customer previousCustomer = Customer.builder()
                .identityNumber(QUERY_ID)
                .name("Test 10")
                .surname("Test 10")
                .phoneNumber("5555555554")
                .income(5000D)
                .build();
        Customer newCustomer = Customer.builder()
                .identityNumber(QUERY_ID)
                .income(1000D)
                .phoneNumber("5555555555")
                .name("Test 1")
                .surname("Test 1")
                .build();

        when(customerRepository.findById(QUERY_ID)).thenReturn(Optional.ofNullable(previousCustomer));
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(newCustomer);

        CustomerDTO customerDTO = customerService.update(QUERY_ID, customerService.toDto(newCustomer));
        assertEquals(customerDTO.getIdentityNumber(), newCustomer.getIdentityNumber());
        assertEquals(customerDTO.getIncome(), newCustomer.getIncome());
        assertEquals(customerDTO.getName(), newCustomer.getName());
        assertEquals(customerDTO.getSurname(), newCustomer.getSurname());
        assertEquals(customerDTO.getPhoneNumber(), newCustomer.getPhoneNumber());

    }

    @Test
    void updateThrowsCustomerNotFoundException() {
        when(customerRepository.findById(QUERY_ID)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.update(QUERY_ID, new CustomerDTO()));
    }

    @Test
    void getById() {

        Customer customer = new Customer(QUERY_ID, "Test 1", "Test 1", 4000D, "5551234455", new ArrayList<>());

        when(customerRepository.findById(QUERY_ID)).thenReturn(java.util.Optional.of(customer));

        CustomerDTO customerDTO = customerService.getById(QUERY_ID);

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
        when(customerRepository.findById(QUERY_ID)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getById(QUERY_ID));
    }

    @Test
    void toDto() {
         Customer customer = Customer.builder()
                .identityNumber(QUERY_ID)
                .income(1000D)
                .phoneNumber("5555555555")
                .name("Test 1")
                .surname("Test 1")
                .build();

         CustomerDTO customerDTO = customerService.toDto(customer);

        assertEquals(customerDTO.getName(), customer.getName());
        assertEquals(customerDTO.getSurname(), customer.getSurname());
        assertEquals(customerDTO.getIncome(), customer.getIncome());
        assertEquals(customerDTO.getIdentityNumber(), customer.getIdentityNumber());
        assertEquals(customerDTO.getPhoneNumber(), customer.getPhoneNumber());
    }

    @Test
    void toEntity() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .identityNumber(QUERY_ID)
                .income(1000D)
                .phoneNumber("5555555555")
                .name("Test 1")
                .surname("Test 1")
                .build();

        Customer customer = customerService.toEntity(customerDTO);

        assertEquals(customer.getName(), customerDTO.getName());
        assertEquals(customer.getSurname(), customerDTO.getSurname());
        assertEquals(customer.getIncome(), customerDTO.getIncome());
        assertEquals(customer.getIdentityNumber(), customerDTO.getIdentityNumber());
        assertEquals(customer.getPhoneNumber(), customerDTO.getPhoneNumber());
    }
}
