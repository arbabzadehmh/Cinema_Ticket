package com.example.cinema_test.controller.exception;

public class DuplicateSaloonException extends Exception{
    public DuplicateSaloonException() {
        super("Duplicate Saloon Name !!!");
    }
}

