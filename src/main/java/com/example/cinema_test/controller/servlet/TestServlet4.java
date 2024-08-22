package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.Show;
import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.entity.Ticket;
import com.example.cinema_test.model.service.ManagerService;
import com.example.cinema_test.model.service.ShowService;
import com.example.cinema_test.model.service.ShowTimeService;
import com.example.cinema_test.model.service.TicketService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@WebServlet(urlPatterns = "/test4.do")
public class TestServlet4 extends HttpServlet {


    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private TicketService ticketService;

    @Inject
    private ManagerService managerService;

    @Inject
    private ShowService showService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

//            LocalDate date = LocalDate.of(2024,8,17);
//            List<Cinema> cinemaList = showTimeService.findDistinctCinemasByShowIdAndDate(4L,date);
//            for (Cinema cinema : cinemaList) {
//                System.out.println(cinema.getName());
//            }
//            System.out.println("\n\n\n");
//            List<ShowTime> showTimeList = showTimeService.findByShowIdAndDate(4L,date);
//            for (ShowTime showTime : showTimeList){
//                System.out.println(showTime.getCinema().getName());
//            }
//
//            System.out.println("\n\n\n\n\n\n");
//            System.out.println(showTimeService.findDistinctDatesByShowId(4L));
//            System.out.println("\n\n\n");
//            List<ShowTime> showTimeList1 = showTimeService.findByShowId(4L);
//            for (ShowTime showTime : showTimeList1){
//                System.out.println(showTime.getStartTime());
//            }


//            System.out.println(ticketService.findSoldSeatsByShowId(4L));


//            for (Ticket ticket : ticketService.findFailedTickets()){
//                System.out.println(ticket.getId());
//            }






        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}
