package com.example.cinema_test.controller.exception;

public class RoleNotFoundException extends Exception{
    public RoleNotFoundException() {
        super("Role Not Found !!!");
    }
}
