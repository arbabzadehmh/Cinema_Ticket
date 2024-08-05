package com.example.cinema_test.controller.exception;

public class SeatNotFoundException extends Exception{
    public SeatNotFoundException() {
        super("Seat Not Found !!!");
    }
}

