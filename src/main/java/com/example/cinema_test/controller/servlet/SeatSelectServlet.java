package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.Seat;
import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.entity.ShowTimeVo;
import com.example.cinema_test.model.entity.User;
import com.example.cinema_test.model.service.*;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;


@WebServlet(urlPatterns = "/seatSelect.do")
public class SeatSelectServlet extends HttpServlet {

    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private TicketService ticketService;

    @Inject
    private SeatService seatService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("seatSelect.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("seatSelect.do\n\n\n\n");


            req.getSession().removeAttribute("cinemas");
            req.getSession().removeAttribute("showDates");
            req.getSession().removeAttribute("allActiveShows");
            req.getSession().removeAttribute("showTimes");


            ShowTime selectedShowTime;

            if (req.getParameter("selectShowTimeId") != null){
                selectedShowTime = showTimeService.findById(Long.parseLong(req.getParameter("selectShowTimeId")));
                ShowTimeVo selectedShowTimeVo = new ShowTimeVo(selectedShowTime);
                req.getSession().setAttribute("selectedShowTime", selectedShowTimeVo);
            } else {
                ShowTimeVo showTimeVo = (ShowTimeVo) req.getSession().getAttribute("selectedShowTime");
                selectedShowTime = showTimeService.findById(showTimeVo.getId());
            }


            User user = (User) req.getSession().getAttribute("user");

            if (selectedShowTime.getRemainingCapacity()>0){


                List<Seat> seats = selectedShowTime.getSaloon().getSeats();
                seats.sort(Comparator.comparing(Seat::getLabel));
                req.getSession().setAttribute("showSeats", seats);


                req.getSession().setAttribute("saloonColumn", selectedShowTime.getSaloon().getSaloonColumn());
                req.getSession().setAttribute("soldSeatsId", ticketService.findSoldSeatsByShowId(selectedShowTime.getId()));
                req.getSession().setAttribute("reservedSeatsId", ticketService.findReservedSeatsByShowId(selectedShowTime.getId()));

                if (user == null || !user.getRole().getRole().equals("customer")) {
                    req.getRequestDispatcher("/customer.do").forward(req, resp);
                } else {
                    req.getRequestDispatcher("/seat-select.jsp").forward(req, resp);
                }




            } else {
                resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "The saloon is full !!!" + "</h1>");
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}
