package com.innova.project.patikafinal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    private Long id;
    private CustomerDTO customer;
    private Double creditAmount;
    private Boolean status;
}
