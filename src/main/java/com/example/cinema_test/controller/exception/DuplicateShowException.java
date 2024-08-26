package com.example.cinema_test.controller.exception;

public class DuplicateShowException extends Exception{
    public DuplicateShowException() {
        super("Duplicate Show Name !!!");
    }
}

