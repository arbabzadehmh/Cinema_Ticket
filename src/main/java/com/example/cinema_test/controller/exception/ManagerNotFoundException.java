package com.example.cinema_test.controller.exception;

public class ManagerNotFoundException extends Exception{
    public ManagerNotFoundException() {
        super("Manager Not Found !!!");
    }
}

