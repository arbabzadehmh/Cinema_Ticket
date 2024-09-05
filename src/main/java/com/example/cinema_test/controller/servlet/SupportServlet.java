package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.Admin;
import com.example.cinema_test.model.entity.Moderator;
import com.example.cinema_test.model.entity.Support;
import com.example.cinema_test.model.entity.User;
import com.example.cinema_test.model.service.AttachmentService;
import com.example.cinema_test.model.service.ModeratorService;
import com.example.cinema_test.model.service.SupportService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Enumeration;


@Slf4j
@WebServlet(urlPatterns ="/support.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)

public class SupportServlet extends HttpServlet {

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








}