package com.example.cinema_test;

public class Test extends Thread {
    @Override
    public void run() {
        System.out.println("salam cinema");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        run();
    }
}
