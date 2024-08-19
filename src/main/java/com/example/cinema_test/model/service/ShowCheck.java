package com.example.cinema_test.model.service;

import com.example.cinema_test.model.entity.Show;
import jakarta.inject.Inject;



public class ShowCheck extends Thread  {

    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private ShowService showService;

    @Override
    public void run() {

        System.out.println("show check");

        try {
            System.out.println(showTimeService.findActiveShows());
            for (Show show : showTimeService.findActiveShows()){
                show.setAvailable(true);
                showService.edit(show);
            }

            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        run();
    }
}
