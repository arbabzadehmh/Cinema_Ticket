package com.example.cinema_test.controller.exception;

public class DuplicateUserException extends Exception{
    public DuplicateUserException() {
        super("Duplicate Username !!!");
    }
}

