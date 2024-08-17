package com.example.cinema_test;

public class Test extends Thread {
    @Override
    public void run() {
        System.out.println("salam");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        run();
    }
}
