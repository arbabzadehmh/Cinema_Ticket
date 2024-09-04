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
import java.util.Enumeration;

@WebServlet(urlPatterns = "/postLogin.do")
public class PostLoginServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("postLogin.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("postLogin.do\n\n\n\n");


            if (req.getRemoteUser() != null) {
                String username = req.getRemoteUser();
                User user = userService.findByUsername(username);

                if (!user.isLocked()){

                    req.getSession().setAttribute("user", user);

                    switch (user.getRole().getRole()) {
                        case "admin":
                            resp.sendRedirect("/admins.do");
                            break;
                        case "moderator":
                            resp.sendRedirect("/moderator.do");
                            break;
                        case "manager":
                            resp.sendRedirect("/managers.do");
                            break;
                        case "customer":
                            resp.sendRedirect("/customer.do");
                            break;
                    }

                } else {
                    resp.sendRedirect("error-423.jsp");
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
