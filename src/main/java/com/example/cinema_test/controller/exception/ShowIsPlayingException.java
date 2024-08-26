package com.example.cinema_test.controller.exception;

public class ShowIsPlayingException extends Exception{
    public ShowIsPlayingException() {
        super("Show is playing on a cinema !!!");
    }
}

