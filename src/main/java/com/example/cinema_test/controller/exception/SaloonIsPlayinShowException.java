package com.example.cinema_test.controller.exception;

public class SaloonIsPlayinShowException extends Exception{
    public SaloonIsPlayinShowException() {
        super("Show is playing on this saloon !!!");
    }
}

