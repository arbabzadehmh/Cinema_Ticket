package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.service.CinemaService;
import com.example.cinema_test.model.service.ShowService;
import com.example.cinema_test.model.service.ShowTimeService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/search.do")
public class SearchServlet extends HttpServlet {


    @Inject
    private ShowService showService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            List<Show> allFoundShows = new ArrayList<>();
            String showText = "";

            if (req.getParameter("showText") != null) {
                showText = req.getParameter("showText");
                allFoundShows = showService.findByText(showText);
            }
            log.info("Search Show by Text: "+ showText);
            req.getSession().setAttribute("allFoundShows", allFoundShows);
            req.getRequestDispatcher("/search.jsp").forward(req, resp);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/search.do");
        }
    }


}
