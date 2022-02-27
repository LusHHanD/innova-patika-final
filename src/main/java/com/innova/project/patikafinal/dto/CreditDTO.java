package com.innova.project.patikafinal.dto;

import lombok.Data;

@Data
public class CreditDTO {
    private Long id;
    private CustomerDTO customer;
    private Double creditAmount;
    private Boolean status;
}
