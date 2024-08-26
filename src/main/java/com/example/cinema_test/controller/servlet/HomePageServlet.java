package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.CinemaVO;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/cinemaHome.do")
public class HomePageServlet extends HttpServlet {

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
                req.getSession().setAttribute("selectedCinemaName", cinemaService.findById(cinemaId).getName());
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

                req.getSession().setAttribute("allActiveShows", showService.findActiveShows());

                req.getRequestDispatcher("/show-select.jsp").forward(req, resp);

            }


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}
