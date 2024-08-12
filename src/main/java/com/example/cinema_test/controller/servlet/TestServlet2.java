package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.Show;
import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.service.*;
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

@WebServlet(urlPatterns = "/test2.do")
public class TestServlet2 extends HttpServlet {

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

            if (req.getParameter("selectCinema") != null) {

                Long cinemaId = Long.parseLong(req.getParameter("selectCinema"));
                System.out.println("selected cinema : " + cinemaId);
                req.getSession().setAttribute("selectedCinema", cinemaService.findById(cinemaId));
                Long showId = Long.parseLong(req.getSession().getAttribute("showId").toString());
                LocalDate selectedDate = LocalDate.parse(req.getSession().getAttribute("selectedDate").toString());

                req.getSession().setAttribute("showTimes", showTimeService.findByShowIdAndDateAndCinemaId(showId, selectedDate, cinemaId));
                req.getRequestDispatcher("/show-time-select.jsp").forward(req, resp);

            } else if (req.getParameter("selectDate") != null) {

                LocalDate selectedDate = LocalDate.parse(req.getParameter("selectDate"));
                req.getSession().setAttribute("selectedDate", selectedDate);
                System.out.println(selectedDate);
                List<Cinema> cinemaList = new ArrayList<>();
                for (ShowTime showTime : showTimeService.findByShowIdAndDate(Long.parseLong(req.getSession().getAttribute("showId").toString()), selectedDate)) {
                    if (!cinemaList.contains(showTime.getCinema())) {
                        cinemaList.add(showTime.getCinema());
                    }
                }

                req.getSession().setAttribute("cinemas", cinemaList);
                req.getRequestDispatcher("/cinema-select.jsp").forward(req, resp);

            } else if (req.getParameter("selectShow") != null) {
                Long showId = Long.parseLong(req.getParameter("selectShow"));
                req.getSession().setAttribute("showId", showId);
                req.getSession().setAttribute("selectedShow", showService.findById(showId));
                System.out.println("show ID : " + showId);
                List<LocalDate> dateList = new ArrayList<>();
                for (ShowTime showTime : showTimeService.findByShowId(showId)) {
                    if (!dateList.contains(showTime.getStartTime().toLocalDate())) {
                        dateList.add(showTime.getStartTime().toLocalDate());
                    }
                }
                System.out.println("Date : " + dateList);
                req.getSession().setAttribute("showDates", dateList);
                req.getRequestDispatcher("/show-date-select.jsp").forward(req, resp);

            } else {

                List<Show> allActiveShows = new ArrayList<>();
                for (ShowTime showTime : showTimeService.findActiveShows()) {
                    if (!allActiveShows.contains(showTime.getShow())) {
                        allActiveShows.add(showTime.getShow());
                    }
                }
                req.getSession().setAttribute("allActiveShows", allActiveShows);

                req.getRequestDispatcher("/show-select.jsp").forward(req, resp);

            }


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}
