package com.innova.project.patikafinal.Exception;

public class CreditNotFoundException extends RuntimeException {
    public CreditNotFoundException(){
        super("Credit record not found.");
    }
}
