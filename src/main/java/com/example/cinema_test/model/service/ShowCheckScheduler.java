package com.example.cinema_test.model.service;

import com.example.cinema_test.model.entity.Show;

import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class ShowCheckScheduler {

    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private ShowService showService;

    @Schedule(hour="*", minute="*", second="*/20", persistent=false)
    public void checkShows() {
        try {
            System.out.println("Checking active shows...");

            for (Show show : showService.findAll()) {
                show.setAvailable(false);
                showService.edit(show);
            }

            for (Show show : showTimeService.findActiveShows()) {
                show.setAvailable(true);
                showService.edit(show);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
