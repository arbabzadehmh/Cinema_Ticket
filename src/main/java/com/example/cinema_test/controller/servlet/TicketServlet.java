package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.entity.enums.ShowType;
import com.example.cinema_test.model.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Slf4j
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

    @Inject
    private CustomerService customerService;


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

                List<Ticket> ticketList = ticketService.findByCustomerPhoneNumber(user.getUsername());
                List<TicketVO> ticketVOList = new ArrayList<>();
                for (Ticket ticket : ticketList) {
                    TicketVO ticketVO = new TicketVO(ticket);
                    ticketVO.setSeatLabel(seatService.findById(ticket.getSeatId()).getLabel());
                    ticketVOList.add(ticketVO);
                }
                req.getSession().setAttribute("customerTickets", ticketVOList);
                redirectPath = "/customers/tickets.jsp";

            } else {

                redirectPath = "/admins/find-ticket.jsp";

            }


            if (req.getParameter("print") != null) {
                Ticket printingTicket = ticketService.findById(Long.parseLong(req.getParameter("print")));
                if (printingTicket.getPayment() == null) {
                    String errorMessage = "No payment found for this ticket !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/ticket.do");
                    return;
                } else {
                    TicketVO ticketVO = new TicketVO(printingTicket);
                    ticketVO.setSeatLabel(seatService.findById(printingTicket.getSeatId()).getLabel());
                    req.getSession().setAttribute("printingTicket", ticketVO);
                    redirectPath = "/tickets/ticket-print.jsp";
                }
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
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/ticket.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/ticket.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ShowTimeVo showTimeVo = (ShowTimeVo) req.getSession().getAttribute("selectedShowTime");
            ShowTime showTime = showTimeService.findById(showTimeVo.getId());
            double basePrice = showTime.getShow().getBasePrice();
            if (showTime.getShow().getShowType().equals(ShowType.MOVIE) && (showTime.getStartTime().getDayOfWeek() == DayOfWeek.TUESDAY || showTime.getStartTime().getDayOfWeek() == DayOfWeek.SATURDAY)) {
                basePrice = basePrice / 2;
            }

            CustomerVO customerVO = (CustomerVO) req.getSession().getAttribute("customer");
            Customer customer = customerService.findById(customerVO.getId());

            List<Long> ticketIds = new ArrayList<>();

            double totalPrice = 0;

            String selectedSeatsParam = req.getParameter("selectedSeats");

            if (selectedSeatsParam != null && !selectedSeatsParam.isEmpty()) {
                List<String> selectedSeatIds = Arrays.asList(selectedSeatsParam.split(","));

                for (String seatId : selectedSeatIds) {

                    Seat seat = seatService.findById(Long.parseLong(seatId));

                    double seatRatio = seat.getPriceRatio();
                    if (showTime.getShow().getShowType().equals(ShowType.MOVIE)) {
                        seatRatio = 1.0;
                    }

                    Ticket ticket =
                            Ticket
                                    .builder()
                                    .customer(customer)
                                    .showTime(showTime)
                                    .price(basePrice * seatRatio)
                                    .issueTime(LocalDateTime.now())
                                    .seatId(seat.getId())
                                    .reserved(true)
                                    .payment(null)
                                    .build();


                    String applicationPath = req.getServletContext().getRealPath("");
                    String uploadDirectory = applicationPath + "uploads/qrCodes";
                    File uploadDir = new File(uploadDirectory);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }


                    // Generate QR Code
                    String qrCodeText = "Ticket ID: " + ticket.getId() + ", Seat: " + seat.getId();
                    String qrCodeFilePath = req.getServletContext().getRealPath("/uploads/qrCodes/") + "ticket_" + ticket.getShowTime().getId() + seatId + ".png";

                    QRCodeGenerator.generateQRCodeImage(qrCodeText, 350, 350, qrCodeFilePath);


                    // Create an attachment for the QR code

                    Attachment qrCodeAttachment = Attachment.builder()
                            .attachTime(LocalDateTime.now())
                            .fileName("/uploads/qrCodes/ticket_" + ticket.getShowTime().getId() + seatId + ".png")
                            .fileType(FileType.PNG)
                            .fileSize(new File(qrCodeFilePath).length())
                            .build();

                    ticket.addAttachment(qrCodeAttachment);

                    ticketService.save(ticket);
                    ticketIds.add(ticket.getId());
                    totalPrice += ticket.getPrice();
                    log.info("Ticket saved successfully ID : " + ticket.getId());

                }

                req.getSession().setAttribute("ticketIds", ticketIds);
                req.getSession().setAttribute("totalPrice", totalPrice);
                req.getSession().setAttribute("banks", bankService.findByStatus(true));
                req.getRequestDispatcher("/payments/payment.jsp").forward(req, resp);

            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/ticket.do");
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Ticket ticketAb;
        try {

            ticketAb = objectMapper.readValue(req.getInputStream(), Ticket.class);
            Ticket editingTicket = (Ticket) req.getSession().getAttribute("editingTicket");
            editingTicket.setReserved(ticketAb.isReserved());
            editingTicket.setEditing(false);
            ticketService.edit(editingTicket);
            log.info("Ticket updated successfully : " + editingTicket.getId());

            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, ticketAb);
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update ticket.\"}");
            out.flush();
        }
    }


}
