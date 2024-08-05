package com.example.cinema_test.controller.exception;

public class AdminNotFoundException extends Exception{
    public AdminNotFoundException() {
        super("Admin Not Found !!!");
    }
}

