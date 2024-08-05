package com.example.cinema_test.controller.exception;

public class ShowTimeNotFoundException extends Exception{
    public ShowTimeNotFoundException() {
        super("ShowTime Not Found !!!");
    }
}

