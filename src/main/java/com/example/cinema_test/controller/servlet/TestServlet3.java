package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.entity.ShowTimeVo;
import com.example.cinema_test.model.service.*;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/test3.do")
public class TestServlet3 extends HttpServlet {

    @Inject
    private CinemaService cinemaService;

    @Inject
    private SaloonService saloonService;

    @Inject
    private ShowService showService;

    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private TicketService ticketService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            ShowTime selectedShowTime = showTimeService.findById(Long.parseLong(req.getParameter("selectShowTimeId")));
            ShowTimeVo selectedShowTimeVo = new ShowTimeVo(selectedShowTime);
            req.getSession().setAttribute("saloonColum", selectedShowTime.getSaloon().getSaloonColumn());
            req.getSession().setAttribute("showSeats", selectedShowTime.getSaloon().getSeats());
            req.getSession().setAttribute("selectedShowTime", selectedShowTimeVo);
            req.getSession().setAttribute("soldSeatsId", ticketService.findSoldSeatsByShowId(selectedShowTime.getId()));
            req.getSession().setAttribute("reservedSeatsId", ticketService.findReservedSeatsByShowId(selectedShowTime.getId()));
            req.getRequestDispatcher("/seat-select.jsp").forward(req, resp);


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}
