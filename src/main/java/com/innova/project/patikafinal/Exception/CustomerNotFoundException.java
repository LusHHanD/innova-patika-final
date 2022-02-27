package com.innova.project.patikafinal.Exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Customer record not found.");
    }
}
