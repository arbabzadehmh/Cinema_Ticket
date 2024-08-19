package com.example.cinema_test.model.service;

import com.example.cinema_test.model.entity.Ticket;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalTime;

@Stateless
public class TicketPaymentCheckScheduler {


    @Inject
    private TicketService ticketService;

    @Schedule(hour="*", minute="*/2", second="0", persistent=false)
    public void checkShows() {
        try {
            System.out.println("Checking failed tickets...");
            System.out.println(LocalTime.now());

            for (Ticket ticket : ticketService.findFailedTickets()) {
                ticket.setReserved(false);
                ticketService.edit(ticket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
