package com.innova.project.patikafinal.creditscore;

import com.innova.project.patikafinal.dto.CustomerDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

@NoArgsConstructor
public class DefaultCreditScoreCalculator implements CreditScoreCalculatorService {

    @Value("${patikafinal.credit.multipler:4}")
    private Integer CREDIT_MULTIPLIER;

    private final int MAX_RAND = 1300;
    private final int MIN_RAND = 200;

    @Override
    public Double calculate(CustomerDTO customer) {
        int creditScore = getCreditScore(customer);
        Double income = customer.getIncome();

        if (creditScore < 500) return 0D;
        if (creditScore < 1000 && income < 5000) return 10000D;
        if (creditScore < 1000 && income > 5000) return 20000D;
        return CREDIT_MULTIPLIER * income;
    }

    private int getCreditScore(CustomerDTO customer) {
        if (customer.getIdentityNumber() == 12345678901L) return 400;
        if (customer.getIdentityNumber() == 12345678902L) return 600;
        if (customer.getIdentityNumber() == 12345678903L) return 700;
        if (customer.getIdentityNumber() == 12345678904L) return 1100;

        return new Random().nextInt(MAX_RAND - MIN_RAND) + MIN_RAND;
    }
}
