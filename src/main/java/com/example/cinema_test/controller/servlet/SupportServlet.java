package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.service.AttachmentService;
import com.example.cinema_test.model.service.MessageService;
import com.example.cinema_test.model.service.ModeratorService;
import com.example.cinema_test.model.service.SupportService;
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
import java.util.Enumeration;
import java.util.List;


@Slf4j
@WebServlet(urlPatterns ="/support.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)

public class SupportServlet extends HttpServlet {

    @Inject
    private MessageService messageService;

    @Inject
    private ModeratorService moderatorService;

    @Inject
    private SupportService supportService;

    @Inject
    private AttachmentService attachmentService;

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
            Moderator loggedModerator = moderatorService.findByUsername(user.getUsername());
            req.getSession().setAttribute("loggedModerator", loggedModerator);


            if (req.getParameter("cancel") != null) {
                Support editingSupport = supportService.findById(Long.parseLong(req.getParameter("cancel")));
                editingSupport.setEditing(false);
                supportService.edit(editingSupport);
                resp.sendRedirect("/support.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Support editingSupport = supportService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingSupport.isEditing()) {
                    editingSupport.setEditing(true);
                    supportService.edit(editingSupport);
                    req.getSession().setAttribute("editingSupport", editingSupport);
                    req.getRequestDispatcher("/support/support-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Record is editing by another user !!!" + "</h1>");
                }
            } else {
                req.getSession().setAttribute("allSupport", supportService.findAll());
                req.getRequestDispatcher("/support/support-panel.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
        }
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Create an ObjectMapper to handle JSON parsing (Jackson library)
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON request body into a Manager object
        Support SupportAb ;
        try {
            SupportAb = objectMapper.readValue(req.getInputStream(), Support.class);
            List<Message> messageList= messageService.findAll();
            messageList.add(SupportAb.getMessageList().get(0));
            req.getSession().setAttribute("allMessage",messageList );
            req.getRequestDispatcher("/support/support-edit.jsp").forward(req, resp);

            Support editingSupport = (Support) req.getSession().getAttribute("editingSupport");
            editingSupport.setMessageList(messageList);
            editingSupport.setCustomer(SupportAb.getCustomer());
            editingSupport.setModerator(SupportAb.getModerator());
            editingSupport.setIssueTime(SupportAb.getIssueTime());
            editingSupport.setSolved(SupportAb.isSolved());
            editingSupport.setEditing(false);
            supportService.edit(editingSupport);
            log.info("support updated successfully : " + editingSupport.getId());



            // Send success response with updated admin
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, SupportAb); // Write admin object as JSON response
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update support.\"}");
            out.flush();
        }
    }










}