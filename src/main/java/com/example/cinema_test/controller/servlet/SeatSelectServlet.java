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
import java.util.Enumeration;


@WebServlet(urlPatterns = "/seatSelect.do")
public class SeatSelectServlet extends HttpServlet {

    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private TicketService ticketService;


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


            ShowTime selectedShowTime = showTimeService.findById(Long.parseLong(req.getParameter("selectShowTimeId")));

            if (selectedShowTime.getRemainingCapacity()>0){

                ShowTimeVo selectedShowTimeVo = new ShowTimeVo(selectedShowTime);
                req.getSession().setAttribute("saloonColum", selectedShowTime.getSaloon().getSaloonColumn());
                req.getSession().setAttribute("showSeats", selectedShowTime.getSaloon().getSeats());
                req.getSession().setAttribute("selectedShowTime", selectedShowTimeVo);
                req.getSession().setAttribute("soldSeatsId", ticketService.findSoldSeatsByShowId(selectedShowTime.getId()));
                req.getSession().setAttribute("reservedSeatsId", ticketService.findReservedSeatsByShowId(selectedShowTime.getId()));
                req.getRequestDispatcher("/seat-select.jsp").forward(req, resp);

            } else {
                resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "The saloon is full !!!" + "</h1>");
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}
