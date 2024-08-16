package com.example.cinema_test.controller.exception;

public class MessageNotFoundException extends Exception{
    public MessageNotFoundException () {
        super("Message Not Found !!!");
    }
}
