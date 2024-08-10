package com.example.cinema_test.controller.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException()  {
        super("User Not Found !!!");
    }
}
