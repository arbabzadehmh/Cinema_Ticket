package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.entity.ShowTimeVo;
import com.example.cinema_test.model.entity.Ticket;
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


@WebServlet(urlPatterns = "/ticket.do")
public class TicketServlet extends HttpServlet {


    @Inject
    private ShowTimeService showTimeService;

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
            ShowTimeVo showTimeVo = (ShowTimeVo) req.getSession().getAttribute("selectedShowTime");

            ShowTime showTime = showTimeService.findById(showTimeVo.getId());

            List<Long> ticketIds = new ArrayList<>();

            String selectedSeatsParam = req.getParameter("selectedSeats");

            if (selectedSeatsParam != null && !selectedSeatsParam.isEmpty()) {
                List<String> selectedSeatIds = Arrays.asList(selectedSeatsParam.split(","));
                for (String seatId : selectedSeatIds) {

                    Ticket ticket =
                            Ticket
                                    .builder()
                                    .customer(null)
                                    .showTime(showTime)
                                    .price(showTime.getShow().getBasePrice())
                                    .issueTime(LocalDateTime.now())
                                    .seatId(Long.parseLong(seatId))
                                    .reserved(true)
                                    .payment(null)
                                    .build();

                    ticketService.save(ticket);
                    ticketIds.add(ticket.getId());

                }

                req.getSession().setAttribute("ticketIds", ticketIds);
                req.getRequestDispatcher("/payment.jsp").forward(req, resp);

            }



        }catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }



}