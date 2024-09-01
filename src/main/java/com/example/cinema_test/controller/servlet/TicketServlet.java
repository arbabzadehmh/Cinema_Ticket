package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.ShowType;
import com.example.cinema_test.model.service.BankService;
import com.example.cinema_test.model.service.SeatService;
import com.example.cinema_test.model.service.ShowTimeService;
import com.example.cinema_test.model.service.TicketService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


@WebServlet(urlPatterns = "/ticket.do")
public class TicketServlet extends HttpServlet {


    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private TicketService ticketService;

    @Inject
    private BankService bankService;

    @Inject
    private SeatService seatService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("ticket.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("ticket.do\n\n\n\n");

            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user != null && user.getRole().getRole().equals("customer")) {
//
                List<Ticket> ticketList = ticketService.findByCustomerPhoneNumber(user.getUsername());
                List<TicketVO> ticketVOList = new ArrayList<>();
                for (Ticket ticket : ticketList) {
                    int seatRow = seatService.findById(ticket.getSeatId()).getRowNumber();
                    int seatNumber = seatService.findById(ticket.getSeatId()).getSeatNumber();
                    TicketVO ticketVO = new TicketVO(ticket);
                    ticketVO.setSeatNumber(seatNumber);
                    ticketVO.setSeatRow(seatRow);
                    ticketVOList.add(ticketVO);
                }
                req.getSession().setAttribute("customerTickets", ticketVOList);
                redirectPath = "/customers/tickets.jsp";

            } else {

                redirectPath = "/admins/find-ticket.jsp";

            }


            if (req.getParameter("print") != null) {
                Ticket printingTicket = ticketService.findById(Long.parseLong(req.getParameter("print")));
                req.getSession().setAttribute("printingTicket", printingTicket);
                req.getRequestDispatcher("/tickets/ticket-print.jsp").forward(req, resp);
            }


            if (req.getParameter("cancel") != null) {
                Ticket editingTicket = ticketService.findById(Long.parseLong(req.getParameter("cancel")));
                editingTicket.setEditing(false);
                ticketService.edit(editingTicket);
                resp.sendRedirect("/ticket.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Ticket editingTicket = ticketService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingTicket.isEditing()) {
                    editingTicket.setEditing(true);
                    ticketService.edit(editingTicket);
                    req.getSession().setAttribute("editingTicket", editingTicket);
                    req.getRequestDispatcher("/tickets/ticket-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Record is editing by another user !!!" + "</h1>");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ShowTimeVo showTimeVo = (ShowTimeVo) req.getSession().getAttribute("selectedShowTime");

            ShowTime showTime = showTimeService.findById(showTimeVo.getId());

            List<Long> ticketIds = new ArrayList<>();

            String selectedSeatsParam = req.getParameter("selectedSeats");

            if (selectedSeatsParam != null && !selectedSeatsParam.isEmpty()) {
                List<String> selectedSeatIds = Arrays.asList(selectedSeatsParam.split(","));

                for (String seatId : selectedSeatIds) {

                    Seat seat = seatService.findById(Long.parseLong(seatId));

                    double seatRatio = seat.getPriceRatio();;
                    if (showTime.getShow().getShowType().equals(ShowType.MOVIE)){
                        seatRatio = 1;
                    }

                    Ticket ticket =
                            Ticket
                                    .builder()
                                    .customer(null)
                                    .showTime(showTime)
                                    .price(showTime.getShow().getBasePrice() * seatRatio)
                                    .issueTime(LocalDateTime.now())
                                    .seatId(seat.getId())
                                    .reserved(true)
                                    .payment(null)
                                    .build();

                    ticketService.save(ticket);
                    ticketIds.add(ticket.getId());

                }

                req.getSession().setAttribute("ticketIds", ticketIds);
                req.getSession().setAttribute("banks", bankService.findAll());
                req.getRequestDispatcher("/payment.jsp").forward(req, resp);

            }
        }catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }



}
