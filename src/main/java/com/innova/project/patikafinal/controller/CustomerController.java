package com.innova.project.patikafinal.controller;

import com.innova.project.patikafinal.Exception.CustomerNotFoundException;
import com.innova.project.patikafinal.dto.CustomerDTO;
import com.innova.project.patikafinal.dto.ExceptionDTO;
import com.innova.project.patikafinal.service.impl.CustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerServiceImpl customerServiceImpl;

    public CustomerController(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @GetMapping
    private ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerServiceImpl.getAll());
    }

    @PostMapping
    private ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerServiceImpl.save(customerDTO));
    }

    @PutMapping("/{id}")
    private ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerServiceImpl.update(id, customerDTO));
    }

    @GetMapping("/{id}")
    private ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerServiceImpl.getById(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerServiceImpl.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    private ResponseEntity<ExceptionDTO> handleCustomerNotFoundException(CustomerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionDTO.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(e.getMessage())
                        .build()
                );
    }
}
