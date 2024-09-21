package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.service.*;
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
@WebServlet(urlPatterns = "/cinemaHome.do")
public class HomePageServlet extends HttpServlet {

    @Inject
    private CinemaService cinemaService;

    @Inject
    private ShowService showService;

    @Inject
    private ShowTimeService showTimeService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("cinemaHome.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("cinemaHome.do\n\n\n\n");


            if (req.getParameter("selectCinema") != null) {

                Long cinemaId = Long.parseLong(req.getParameter("selectCinema"));
                Cinema cinema = cinemaService.findById(cinemaId);
                CinemaVO cinemaVO = new CinemaVO(cinema);
                req.getSession().setAttribute("selectedCinema", cinemaVO);
                Long showId = Long.parseLong(req.getSession().getAttribute("showId").toString());
                LocalDate selectedDate = LocalDate.parse(req.getSession().getAttribute("selectedDate").toString());
                List<ShowTime> showTimes = showTimeService.findByShowIdAndDateAndCinemaId(showId, selectedDate, cinemaId);
                List<ShowTimeVo> showTimeVoList = new ArrayList<>();
                for (ShowTime showTime : showTimes) {
                    ShowTimeVo showTimeVo = new ShowTimeVo(showTime);
                    showTimeVoList.add(showTimeVo);
                }


                req.getSession().setAttribute("showTimes", showTimeVoList);
                req.getRequestDispatcher("/show-time-select.jsp").forward(req, resp);

            } else if (req.getParameter("selectDate") != null) {

                LocalDate selectedDate = LocalDate.parse(req.getParameter("selectDate"));
                req.getSession().setAttribute("selectedDate", selectedDate);
                List<Cinema> cinemaList = showTimeService.findDistinctCinemasByShowIdAndDate(Long.parseLong(req.getSession().getAttribute("showId").toString()),selectedDate);
                List<CinemaVO> cinemaVOList = new ArrayList<>();
                for (Cinema cinema : cinemaList) {
                    CinemaVO cinemaVO = new CinemaVO(cinema);
                    cinemaVOList.add(cinemaVO);
                }

                req.getSession().setAttribute("cinemas", cinemaVOList);
                req.getRequestDispatcher("/cinema-select.jsp").forward(req, resp);

            } else if (req.getParameter("selectShow") != null) {
                Long showId = Long.parseLong(req.getParameter("selectShow"));
                req.getSession().setAttribute("showId", showId);
                req.getSession().setAttribute("selectedShow", showService.findById(showId));
                List<LocalDate> dateList = showTimeService.findDistinctDatesByShowId(showId);

                req.getSession().setAttribute("showDates", dateList);
                req.getRequestDispatcher("/show-date-select.jsp").forward(req, resp);

            } else {

                List<Show> allActiveShows = new ArrayList<>();

                if(req.getParameter("findMovies") != null) {
                    allActiveShows = showService.findAvailableShowsByType("MOVIE");
                } else if(req.getParameter("findTheaters") != null) {
                    allActiveShows = showService.findAvailableShowsByType("THEATER");
                } else if (req.getParameter("findEvents") != null) {
                    allActiveShows = showService.findAvailableShowsByType("EVENT");
                } else if (req.getParameter("findConcerts") != null) {
                    allActiveShows = showService.findAvailableShowsByType("CONCERT");
                } else {
                    allActiveShows = showService.findAvailableShows();
                }



                for (Show show : allActiveShows){
                    if (show.getAttachments() != null && !show.getAttachments().isEmpty()){
                        Collections.shuffle(show.getAttachments());
                    }
                }

                req.getSession().setAttribute("allActiveShows", allActiveShows);

                req.getRequestDispatcher("/show-select.jsp").forward(req, resp);

            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/cinemaHome.do");
        }
    }


}
