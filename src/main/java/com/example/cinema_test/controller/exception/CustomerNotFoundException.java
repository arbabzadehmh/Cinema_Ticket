package com.example.cinema_test.controller.exception;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException() {
        super("Customer Not Found !!!");
    }
}

