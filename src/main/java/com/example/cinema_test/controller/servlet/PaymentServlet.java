package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.service.AttachmentService;
import com.example.cinema_test.model.service.BankService;
import com.example.cinema_test.model.service.PaymentService;
import com.example.cinema_test.model.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/payment.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class PaymentServlet extends HttpServlet {

    @Inject
    private PaymentService paymentService;

    @Inject
    private AttachmentService attachmentService;

    @Inject
    private TicketService ticketService;

    @Inject
    private BankService bankService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("payment.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("payment.do\n\n\n\n");

            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user != null && user.getRole().getRole().equals("customer")) {
                List<Payment> paymentList = paymentService.findByUsername(user.getUsername());
                List<PaymentVO> paymentVOList = new ArrayList<>();
                for (Payment payment : paymentList) {
                    PaymentVO paymentVO = new PaymentVO(payment);
                    paymentVOList.add(paymentVO);
                }
                req.getSession().setAttribute("paymentList", paymentVOList);
                redirectPath = "/customers/payments.jsp";
            } else {
                redirectPath = "/admins/find-payment.jsp";
            }

            if (req.getParameter("cancel") != null) {
                Payment editingPayment = paymentService.findById(Long.parseLong(req.getParameter("cancel")));
                editingPayment.setEditing(false);
                paymentService.edit(editingPayment);
                resp.sendRedirect("/payment.do");
                return;
            }

            if (req.getParameter("edit") != null){
                Payment editingPayment = paymentService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingPayment.isEditing()){
                    editingPayment.setEditing(true);
                    paymentService.edit(editingPayment);
                    req.getSession().setAttribute("editingPayment", editingPayment);
                    req.getRequestDispatcher("/payments/payment-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/payment.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/payment.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getPart("newImage") != null) {
                Payment editingPayment = (Payment) req.getSession().getAttribute("editingPayment");

                for (Attachment attachment : editingPayment.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingPayment.getAttachments().clear();


                Part filePart = req.getPart("newImage");

                String applicationPath = req.getServletContext().getRealPath("");

                String uploadDirectory = applicationPath + "uploads";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String fileName = filePart.getSubmittedFileName();
                String filePath = uploadDirectory + File.separator + fileName;
                String relativePath = "/uploads/" + fileName;

                filePart.write(filePath);


                Attachment attachment = Attachment.builder()
                        .attachTime(LocalDateTime.now())
                        .fileName(relativePath)
                        .fileType(FileType.JPG)
                        .fileSize(filePart.getSize())
                        .build();

                editingPayment.addAttachment(attachment);
                editingPayment.setEditing(false);
                paymentService.edit(editingPayment);
                log.info("Payment image changed successfully-ID : " + editingPayment.getId());
                resp.sendRedirect("/payment.do");


            } else {

                double totalPrice =(Double) req.getSession().getAttribute("totalPrice");

                List<Ticket> tickets = new ArrayList<>();
                List<Long> ticketIds = (List<Long>) req.getSession().getAttribute("ticketIds");
                for (Long ticketId : ticketIds) {
                    Ticket ticket = ticketService.findById(ticketId);
                    tickets.add(ticket);
                }

                Bank bank = bankService.findByName(req.getParameter("bank"));

                Payment payment =
                        Payment
                                .builder()
                                .price(totalPrice)
                                .paymentDateTime(LocalDateTime.now())
                                .description(req.getParameter("description"))
                                .bank(bank)
                                .ticketList(tickets)
                                .deleted(false)
                                .build();


                Part filePart = req.getPart("image");

                if (filePart != null && filePart.getSize() > 0) {
                    String applicationPath = req.getServletContext().getRealPath("");

                    String uploadDirectory = applicationPath + "uploads";
                    File uploadDir = new File(uploadDirectory);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String fileName = filePart.getSubmittedFileName();
                    String filePath = uploadDirectory + File.separator + fileName;
                    String relativePath = "/uploads/" + fileName;

                    filePart.write(filePath);

                    Attachment attachment = Attachment.builder()
                            .attachTime(LocalDateTime.now())
                            .fileName(relativePath)
                            .fileType(FileType.JPG)
                            .fileSize(filePart.getSize())
                            .build();

                    payment.addAttachment(attachment);
                }

                BeanValidator<Payment> paymentValidator = new BeanValidator<>();
                if (paymentValidator.validate(payment).isEmpty()) {
                    paymentService.save(payment);

                    bank.setAccountBalance(bank.getAccountBalance() + totalPrice);
                    bankService.edit(bank);

                    for (Ticket ticket : tickets) {
                        if(ticket.getPayment() == null){
                            ticket.setPayment(payment);
                            ticketService.edit(ticket);
                        }
                    }

                    log.info("Payment saved successfully : " + payment.getId());
                    req.getRequestDispatcher("/payments/successful-payment.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Invalid Payment Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/payment.do");
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/payment.do");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Payment paymentAb;
        try {

            paymentAb = objectMapper.readValue(req.getInputStream(), Payment.class);
            Payment editingPayment = (Payment) req.getSession().getAttribute("editingPayment");
            editingPayment.setEditing(false);
            editingPayment.setDescription(paymentAb.getDescription());
            paymentService.edit(editingPayment);
            log.info("Payment updated successfully : " + editingPayment.getId());

            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, paymentAb);
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update payment.\"}");
            out.flush();
        }
    }

}
