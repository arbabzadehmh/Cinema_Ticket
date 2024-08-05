package com.example.cinema_test.controller.exception;

public class SaloonNotFoundException extends Exception{
    public SaloonNotFoundException() {
        super("Saloon Not Found !!!");
    }
}

