package com.innova.project.patikafinal.service.impl;

import com.innova.project.patikafinal.Exception.CreditNotFoundException;
import com.innova.project.patikafinal.creditscore.CreditScoreCalculatorService;
import com.innova.project.patikafinal.creditscore.DefaultCreditScoreCalculator;
import com.innova.project.patikafinal.dto.CreditApplicationResponseDTO;
import com.innova.project.patikafinal.dto.CreditDTO;
import com.innova.project.patikafinal.dto.CustomerDTO;
import com.innova.project.patikafinal.entity.Credit;
import com.innova.project.patikafinal.entity.Customer;
import com.innova.project.patikafinal.repository.CreditRepository;
import com.innova.project.patikafinal.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {
    @Mock
    private CreditRepository creditRepository;
    @Mock
    private CustomerRepository customerRepository;


    private CreditScoreCalculatorService creditScoreCalculatorService;

    private CreditServiceImpl creditService;
    private CustomerServiceImpl customerService;

    private final Customer MOCK_CUSTOMER = Customer.builder()
            .identityNumber(12345678901L)
            .income(1000D)
            .phoneNumber("5555555555")
            .name("Test 1")
            .surname("Test 1")
            .build();

    private final Long QUERY_ID = 1L;

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(customerRepository, new ModelMapper());
        creditScoreCalculatorService = new DefaultCreditScoreCalculator();
        ReflectionTestUtils.setField(creditScoreCalculatorService, "CREDIT_MULTIPLIER", 4);
        creditService = new CreditServiceImpl(creditRepository, creditScoreCalculatorService, customerService, new ModelMapper());
    }

    @Test
    void getAll() {
        List<Credit> creditList = Arrays.asList(
                new Credit(1L, MOCK_CUSTOMER, 5000D, Boolean.TRUE),
                new Credit(2L, MOCK_CUSTOMER, 0D, Boolean.FALSE)
        );

        when(creditRepository.findAll()).thenReturn(creditList);

        List<CreditDTO> creditDTOList = creditService.getAll();
        creditList.forEach(credit -> {
            CreditDTO actual = creditDTOList.stream().filter(item -> item.getId().equals(credit.getId())).findFirst().get();

            assertEquals(actual.getId(), credit.getId());
            assertEquals(actual.getCreditAmount(), credit.getCreditAmount());
            assertEquals(actual.getStatus(), credit.getStatus());
            assertEquals(actual.getCustomer().getIdentityNumber(), credit.getCustomer().getIdentityNumber());

        });

    }

    @Test
    void getById() {
        Credit credit = new Credit(1L, MOCK_CUSTOMER, 5000D, Boolean.TRUE);

        when(creditRepository.findById(QUERY_ID)).thenReturn(Optional.of(credit));

        CreditDTO creditDTO = creditService.getById(QUERY_ID);

        assertEquals(creditDTO.getId(), credit.getId());
        assertEquals(creditDTO.getCreditAmount(), credit.getCreditAmount());
        assertEquals(creditDTO.getStatus(), credit.getStatus());
        assertEquals(creditDTO.getCustomer().getIdentityNumber(), credit.getCustomer().getIdentityNumber());
    }

    @Test
    void creditNotFound() {
        when(creditRepository.findById(QUERY_ID)).thenReturn(Optional.empty());

        assertThrows(CreditNotFoundException.class, () -> creditService.getById(QUERY_ID));
    }

    @Test
    void deleteById() {
        creditService.deleteById(QUERY_ID);
        verify(creditRepository).deleteById(Mockito.any(Long.class));
    }

    @Test
    void getByCustomerIdentityNumber() {
        List<Credit> creditList = Arrays.asList(
                new Credit(1L, MOCK_CUSTOMER, 5000D, Boolean.TRUE),
                new Credit(2L, MOCK_CUSTOMER, 0D, Boolean.FALSE)
        );

        when(creditRepository.findByCustomer_IdentityNumber(MOCK_CUSTOMER.getIdentityNumber())).thenReturn(Optional.of(creditList));

        List<CreditDTO> creditDTOList = creditService.getByCustomerIdentityNumber(MOCK_CUSTOMER.getIdentityNumber());

        creditList.forEach(credit -> {
            CreditDTO actual = creditDTOList.stream().filter(item -> item.getId().equals(credit.getId())).findFirst().get();

            assertEquals(actual.getId(), credit.getId());
            assertEquals(actual.getCreditAmount(), credit.getCreditAmount());
            assertEquals(actual.getStatus(), credit.getStatus());
            assertEquals(actual.getCustomer().getIdentityNumber(), credit.getCustomer().getIdentityNumber());

        });
    }

    @Test
    void rejectedApplication() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .identityNumber(12345678901L)
                .name("Test 1")
                .surname("Test 2")
                .income(2000D)
                .phoneNumber("5555555554")
                .build();
        when(customerRepository.findById(customerDTO.getIdentityNumber())).thenReturn(Optional.ofNullable(customerService.toEntity(customerDTO)));

        CreditApplicationResponseDTO result = creditService.apply(customerDTO.getIdentityNumber());

        assertNull(result.getCreditLimit());
        assertEquals(result.getApplicationStatus(), Boolean.FALSE);
    }

    @Test
    void approvedApplication() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .identityNumber(12345678902L)
                .name("Test 1")
                .surname("Test 2")
                .income(5000D)
                .phoneNumber("5555555554")
                .build();
        when(customerRepository.findById(customerDTO.getIdentityNumber())).thenReturn(Optional.ofNullable(customerService.toEntity(customerDTO)));

        CreditApplicationResponseDTO result = creditService.apply(customerDTO.getIdentityNumber());

        assertNotNull(result.getCreditLimit());
        assertEquals(result.getApplicationStatus(), Boolean.TRUE);
    }

    @Test
    void toDto() {
        Credit credit = Credit.builder()
                .id(1L)
                .customer(MOCK_CUSTOMER)
                .status(Boolean.TRUE)
                .creditAmount(1000D)
                .build();

        CreditDTO creditDTO = creditService.toDto(credit);

        assertEquals(creditDTO.getId(), creditDTO.getId());
        assertEquals(creditDTO.getCreditAmount(), creditDTO.getCreditAmount());
        assertEquals(creditDTO.getStatus(), creditDTO.getStatus());
        assertEquals(creditDTO.getCustomer().getIdentityNumber(), credit.getCustomer().getIdentityNumber());
    }

    @Test
    void toEntity() {
        CreditDTO creditDTO = CreditDTO.builder()
                .id(1L)
                .customer(customerService.toDto(MOCK_CUSTOMER))
                .status(Boolean.TRUE)
                .creditAmount(1000D)
                .build();

        Credit credit = creditService.toEntity(creditDTO);

        assertEquals(credit.getId(), creditDTO.getId());
        assertEquals(credit.getCreditAmount(), creditDTO.getCreditAmount());
        assertEquals(credit.getStatus(), creditDTO.getStatus());
        assertEquals(credit.getCustomer().getIdentityNumber(), creditDTO.getCustomer().getIdentityNumber());


    }
}
