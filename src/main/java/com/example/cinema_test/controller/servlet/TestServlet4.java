package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.service.*;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Inject
    private UserService userService;

    @Inject
    CinemaService cinemaService;

    @Inject
    private SaloonService saloonService;

    @Inject
    private PaymentService paymentService;


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


//            String username = req.getRemoteUser();
//            User user = userService.findByUsername(username);
//            req.getSession().setAttribute("user", user);

//            LocalDateTime startTime = LocalDateTime.of(2024,8,29,15,15);
//            LocalDateTime endTime = LocalDateTime.of(2024,8,29,17,30);
//            List<ShowTime> showTimeList = showTimeService.findShowtimeBySaloonIdAndTime(4L,startTime,endTime );
//            for (ShowTime showTime : showTimeList){
//                System.out.println(showTime.getId());
//            }


//            System.out.println(saloonService.findSaloonSeats(1L).size());


//            System.out.println(ticketService.findById(71L).getAttachments().get(0).getFileName());
//            System.out.println(ticketService.findById(71L).getAttachments().get(1).getFileName());

//            System.out.println(ticketService.findById(72L).getAttachments().get(0).getFileName());
//            System.out.println(ticketService.findById(72L).getAttachments().get(1).getFileName());


//            System.out.println(paymentService.findByDate(LocalDate.of(2024,9,14)).size());

            System.out.println(showService.findAvailableShowsByType("THEATER").size());
            System.out.println(showService.findAvailableShowsByType("EVENT").size());
            System.out.println(showService.findAvailableShowsByType("MOVIE").size());
            System.out.println(showService.findAvailableShowsByType("CONCERT").size());


        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


}
