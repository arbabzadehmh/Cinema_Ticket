package com.example.cinema_test.controller.exception;

public class CinemaNotFoundException extends Exception{
    public CinemaNotFoundException() {
        super("Cinema Not Found !!!");
    }
}

