package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.entity.Ticket;
import com.example.cinema_test.model.service.PaymentService;
import com.example.cinema_test.model.service.ShowTimeService;
import com.example.cinema_test.model.service.TicketService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@WebServlet(urlPatterns = "/payment.do")
public class PaymentServlet extends HttpServlet {


    @Inject
    private PaymentService paymentService;

    @Inject
    private TicketService ticketService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {



        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {






        }catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }



}
