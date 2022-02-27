package com.innova.project.patikafinal.service;

import com.innova.project.patikafinal.dto.CustomerDTO;
import com.innova.project.patikafinal.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAll();

    CustomerDTO save(CustomerDTO customerDTO);

    CustomerDTO update(Long id, CustomerDTO customerDTO);

    void deleteById(Long id);

    CustomerDTO getById(Long id);

    CustomerDTO toDto(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);
}
