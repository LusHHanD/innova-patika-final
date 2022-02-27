package com.innova.project.patikafinal.creditscore;

import com.innova.project.patikafinal.dto.CustomerDTO;
import com.innova.project.patikafinal.entity.Customer;

public interface CreditScoreCalculatorService {
    Double calculate(CustomerDTO customer);
}
