package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(urlPatterns = "/logout.do")
public class Logout extends HomePageServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.getSession().invalidate();
        log.info(user.getUsername() + " logged out successfully");
        resp.sendRedirect("/postLogin.do");
    }
}
