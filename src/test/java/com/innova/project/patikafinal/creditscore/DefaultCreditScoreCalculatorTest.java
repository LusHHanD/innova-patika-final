package com.innova.project.patikafinal.creditscore;

import com.innova.project.patikafinal.entity.Customer;
import com.innova.project.patikafinal.repository.CustomerRepository;
import com.innova.project.patikafinal.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DefaultCreditScoreCalculatorTest {
    private DefaultCreditScoreCalculator creditScoreCalculatorService;

    @Mock
    private CustomerRepository customerRepository;


    private CustomerServiceImpl customerService;

    @BeforeEach
    void setup() {
        creditScoreCalculatorService = new DefaultCreditScoreCalculator();
        ReflectionTestUtils.setField(creditScoreCalculatorService, "CREDIT_MULTIPLIER", 4);
        customerService = new CustomerServiceImpl(customerRepository, new ModelMapper());
    }

    @Test
    void calculate() {
        Map<Long, Double> expectedResults = Map.of(12345678901L, 0D, 12345678902L, 10000D, 12345678903L, 20000D, 12345678904L, 360000D);
        List<Customer> customerList = Arrays.asList(
                new Customer(12345678901L, "Test 1", "Test 1", 4000D, "5551234455", new ArrayList<>()),
                new Customer(12345678902L, "Test 2", "Test 2", 4000D, "5551234456", new ArrayList<>()),
                new Customer(12345678903L, "Test 3", "Test 3", 6000D, "5551234457", new ArrayList<>()),
                new Customer(12345678904L, "Test 4", "Test 4", 90000D, "5551234458", new ArrayList<>())
        );

        customerList.stream().forEach(customer -> assertEquals(expectedResults.get(customer.getIdentityNumber()), creditScoreCalculatorService.calculate(customerService.toDto(customer))));


    }
}
