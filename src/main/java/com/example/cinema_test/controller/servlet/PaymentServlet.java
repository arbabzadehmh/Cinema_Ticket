package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.Payment;
import com.example.cinema_test.model.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;


@WebServlet(urlPatterns = "/payment.do")
public class PaymentServlet extends HttpServlet {

    @Inject
    private PaymentService paymentService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("payment.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("payment.do\n\n\n\n");


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Payment payment = new Payment();
            payment.setPrice(Double.parseDouble(req.getParameter("price")));
            payment.setPaymentDateTime(LocalDateTime.parse(req.getParameter("paymentDateTime")));
            payment.setDescription(req.getParameter("description"));

            paymentService.save(payment);
            resp.sendRedirect(req.getContextPath() + "/payment");
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Payment payment = new Payment();
            payment.setPrice(Double.parseDouble(req.getParameter("price")));
            payment.setPaymentDateTime(LocalDateTime.parse(req.getParameter("paymentDateTime")));
            payment.setDescription(req.getParameter("description"));


            String id = req.getParameter("id");

            if (id != null && !id.isEmpty()) {
                payment.setId(Long.parseLong(id));
                paymentService.edit(payment);
                resp.getWriter().write("Payment updated successfully.");
            } else {
                resp.getWriter().write("Payment ID is required for updating.");
            }

        } catch (Exception e) {

        }
    }

}
