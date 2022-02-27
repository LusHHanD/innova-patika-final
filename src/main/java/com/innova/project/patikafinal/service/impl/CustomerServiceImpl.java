package com.innova.project.patikafinal.service.impl;

import com.innova.project.patikafinal.Exception.CustomerNotFoundException;
import com.innova.project.patikafinal.dto.CustomerDTO;
import com.innova.project.patikafinal.entity.Customer;
import com.innova.project.patikafinal.repository.CustomerRepository;
import com.innova.project.patikafinal.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public void deleteById(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = customerRepository.save(toEntity(customerDTO));

        return toDto(customer);
    }

    @Override
    public CustomerDTO update(Long id, CustomerDTO customerDTO) {
        customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);

        return toDto(customerRepository.save(toEntity(customerDTO)));
    }

    @Override
    public CustomerDTO getById(Long id) {
        return customerRepository.findById(id).map(this::toDto).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public List<CustomerDTO> getAll() {
        List<Customer> customerList = new ArrayList<>(customerRepository.findAll());

        return customerList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO toDto(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public Customer toEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}
