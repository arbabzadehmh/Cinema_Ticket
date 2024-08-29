package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.User;
import com.example.cinema_test.model.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/postLogin.do")
public class PostLoginServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getRemoteUser() != null) {
                String username = req.getRemoteUser();
                User user = userService.findByUsername(username);
                req.getSession().setAttribute("user", user);

                switch (user.getRole().getRole()) {
                    case "admin":
                        resp.sendRedirect("/admins.do");
                        break;
                    case "moderator":
                        resp.sendRedirect("moderator-panel.jsp");
                        break;
                    case "manager":
                        resp.sendRedirect("/managers.do");
                        break;
                    case "customer":
                        resp.sendRedirect("customer-panel.jsp");
                        break;
                }

            } else {
                // Default to login page if no valid role
                resp.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
