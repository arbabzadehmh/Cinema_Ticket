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
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@WebServlet(urlPatterns = "/support.do")
public class SupportServlet extends HttpServlet {

    @Inject
    private ModeratorService moderatorService;

    @Inject
    private SupportService supportService;

    @Inject
    private CustomerService customerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("support.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("support.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user != null && user.getRole().getRole().equals("customer")) {
                Customer customer = customerService.findByUsername(user.getUsername());
                List<Support> supportList = supportService.findByCustomer(customer);

                req.getSession().setAttribute("supportList", supportList);
                redirectPath = "/customers/supports.jsp";
            } else {
                req.getSession().setAttribute("allNotSolvedSupport", supportService.findByNotSolved());
                redirectPath = "/supports/support-panel.jsp";
            }

            if (req.getParameter("solve") != null) {
                Support support = supportService.findById(Long.parseLong(req.getParameter("solve")));
                support.setSolved(true);
                supportService.edit(support);
                resp.sendRedirect("/support.do");
                return;
            }

            if (req.getParameter("open") != null) {
                Support openingSupport = supportService.findById(Long.parseLong(req.getParameter("open")));
                req.getSession().setAttribute("openingSupport", openingSupport);
                req.getRequestDispatcher("message.do").forward(req, resp);
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/support.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            User user = (User) req.getSession().getAttribute("user");
            Customer customer = customerService.findByUsername(user.getUsername());

            List<Moderator> moderators = moderatorService.findAll();
            if (!moderators.isEmpty()) {
                Random random = new Random();
                int randomIndex = random.nextInt(moderators.size());
                Moderator randomModerator = moderators.get(randomIndex);

                Message message =
                        Message
                                .builder()
                                .text("How can I help you ?")
                                .sendTime(LocalDateTime.now())
                                .sender("moderator")
                                .support(null)
                                .deleted(false)
                                .build();

                Support support =
                        Support
                                .builder()
                                .customer(customer)
                                .moderator(randomModerator)
                                .issueTime(LocalDateTime.now())
                                .deleted(false)
                                .build();

                support.addMessage(message);

                message.setSupport(support);

                supportService.save(support);
                log.info("Support saved successfully : " + support.getId());
                req.getSession().setAttribute("openingSupport", support);
                resp.sendRedirect("message.do");
            } else {
                String errorMessage = "No moderators found !!!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/support.do");
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/support.do");
        }
    }


}