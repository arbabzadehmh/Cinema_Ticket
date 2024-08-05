package com.example.cinema_test.controller.exception;

public class ShowNotFoundException extends Exception{
    public ShowNotFoundException() {
        super("Show Not Found !!!");
    }
}

