package com.example.cinema_test.controller.exception;

public class TicketNotFoundException extends Exception{
    public TicketNotFoundException() {
        super("Ticket Not Found !!!");
    }
}

