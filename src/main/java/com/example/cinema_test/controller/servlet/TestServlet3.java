package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.Show;
import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.service.CinemaService;
import com.example.cinema_test.model.service.SaloonService;
import com.example.cinema_test.model.service.ShowService;
import com.example.cinema_test.model.service.ShowTimeService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            ShowTime selectedShowTime = showTimeService.findById(Long.parseLong(req.getParameter("selectShowTimeId")));
            req.getSession().setAttribute("showSeats", selectedShowTime.getShowSeats());
            req.getSession().setAttribute("selectedShowTime", selectedShowTime);
            req.getRequestDispatcher("/seat-select.jsp").forward(req, resp);


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}