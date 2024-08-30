package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.controller.exception.ExceptionWrapper;

import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.List;


@Slf4j
@WebServlet(urlPatterns = "/showtime.do")
public class ShowTimeServlet extends HttpServlet {


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

//            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
//            System.out.println("showtime.do");
//            while (attributeNames.hasMoreElements()) {
//                String attributeName = attributeNames.nextElement();
//                System.out.println("Attribute Name: " + attributeName);
//            }
//            System.out.println("showtime.do\n\n\n\n");

            User user = (User) req.getSession().getAttribute("user");

            Cinema cinema = null;
            String redirectPath = "";

            if (user.getRole().getRole().equals("manager")) {

                ManagerVO managerVO = (ManagerVO) req.getSession().getAttribute("manager");
                cinema = cinemaService.findByName(managerVO.getCinemaName());
                redirectPath = "/managers/manager-show-time.jsp";

            } else if (user.getRole().getRole().equals("admin") || user.getRole().getRole().equals("moderator")) {

                if (req.getParameter("cinemaId") != null){
                    cinema = cinemaService.findById(Long.parseLong(req.getParameter("cinemaId")));
                    CinemaVO cinemaVO = new CinemaVO(cinema);
                    req.getSession().setAttribute("cinema", cinemaVO);
                } else {
                    CinemaVO cinemaVO = (CinemaVO) req.getSession().getAttribute("cinema");
                    cinema = cinemaService.findById(cinemaVO.getId());
                }

                redirectPath = "/cinemas/cinema-show-time.jsp";
            }

            List<ShowTime> cinemaShowTimes = cinema.getShowTimeList();
            List<Saloon> cinemaSaloons = cinemaService.findCinemaActiveSaloons(cinema.getId());
            List<Show> cinemaShows = cinemaService.findCinemaActiveShows(cinema.getId());


            if (req.getParameter("cancel") != null) {
                ShowTime editingShowTime = showTimeService.findById(Long.parseLong(req.getParameter("cancel")));
                editingShowTime.setEditing(false);
                showTimeService.edit(editingShowTime);
                resp.sendRedirect("/showtime.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                ShowTime editingShowTime = showTimeService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingShowTime.isEditing()) {
                    editingShowTime.setEditing(true);
                    showTimeService.edit(editingShowTime);
                    req.getSession().setAttribute("editingShowTime", editingShowTime);
                    req.getRequestDispatcher("/showTimes/show-time-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Record is editing by another user !!!" + "</h1>");
                }
            } else {
                req.getSession().setAttribute("cinemaShowTimes", cinemaShowTimes);
                req.getSession().setAttribute("cinemaSaloons", cinemaSaloons);
                req.getSession().setAttribute("allUsableShows", cinemaShows);
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            log.error(ExceptionWrapper.getMessage(e).toString());
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            CinemaVO cinemaVO = (CinemaVO) req.getSession().getAttribute("cinema");
            Cinema cinema = cinemaService.findById(cinemaVO.getId());

            Saloon saloon = cinemaService.findSaloonByCinemaIdAndSaloonNumber(cinema.getId(), Integer.parseInt(req.getParameter("saloonNumber")));

            LocalDate showTimeDate = LocalDate.parse(req.getParameter("date"));
            LocalTime startHour = LocalTime.parse(req.getParameter("startTime"));
            LocalTime endHour = LocalTime.parse(req.getParameter("endTime"));
            LocalDateTime startTime = showTimeDate.atTime(startHour);
            LocalDateTime endTime = showTimeDate.atTime(endHour);


            ShowTime showTime =
                    ShowTime
                            .builder()
                            .saloon(saloon)
                            .show(showService.findByName(req.getParameter("show")))
                            .remainingCapacity(saloon.getCapacity())
                            .startTime(startTime)
                            .endTime(endTime)
                            .status(Boolean.parseBoolean(req.getParameter("status")))
                            .description(req.getParameter("description"))
                            .cinema(cinema)
                            .deleted(false)
                            .build();

            List<ShowTime> interferenceShowTimes = showTimeService.findShowtimeBySaloonIdAndTime(saloon.getId(), startTime, endTime);
            if (interferenceShowTimes == null || interferenceShowTimes.isEmpty()) {
                showTimeService.save(showTime);
                cinema.addShowTime(showTime);
                cinemaService.edit(cinema);
                resp.sendRedirect("/showtime.do");
                log.info("ShowTime saved successfully-ID : " + showTime.getId());

            } else {
                resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "The Selected Saloon Is Occupied In This Time !!!" + "</h1>");
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            log.error(ExceptionWrapper.getMessage(e).toString());
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        ShowTimeVo showTimeVo;

        try {
            showTimeVo = objectMapper.readValue(req.getInputStream(), ShowTimeVo.class);
            ShowTime editingShowTime = (ShowTime) req.getSession().getAttribute("editingShowTime");
            editingShowTime.setShow(showService.findByName(showTimeVo.getShowName()));
            editingShowTime.setStatus(showTimeVo.isStatus());
            editingShowTime.setDescription(showTimeVo.getDescription());
            editingShowTime.setEditing(false);
            showTimeService.edit(editingShowTime);

            log.info("ShowTime edited successfully-ID : " + editingShowTime.getId());

            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, showTimeVo); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(ExceptionWrapper.getMessage(e).toString());

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update showtime.\"}");
            out.flush();
        }

    }


}
