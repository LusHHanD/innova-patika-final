package com.innova.project.patikafinal.configuration;

import com.innova.project.patikafinal.creditscore.CreditScoreCalculatorService;
import com.innova.project.patikafinal.creditscore.DefaultCreditScoreCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreditScoreCalculatorConfig {
    @Bean
    CreditScoreCalculatorService calculatorService(){
        return new DefaultCreditScoreCalculator();
    }
}
