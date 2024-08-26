package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.Admin;
import com.example.cinema_test.model.entity.User;
import com.example.cinema_test.model.service.AdminService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admins.do")
public class AdminServlet extends HttpServlet {

    @Inject
    private AdminService adminService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

   try{
       if (req.getSession().getAttribute("edit") != null) {
           Admin editingAdmin = (Admin) req.getSession().getAttribute("edit");
           if (!editingAdmin.isEditing()){
               editingAdmin.setEditing(true);
               adminService.edit(editingAdmin);
               req.getSession().setAttribute("edit", editingAdmin);
               req.getRequestDispatcher("/admins/admin-edit.jsp").forward(req, resp);
           } else {
               resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Record is editing by another user !!!" + "</h1>");
           }
       }else {
           User user = (User) req.getSession().getAttribute("user");
           req.getSession().setAttribute("admin", adminService.findByUsername(user.getUsername()));
           req.getRequestDispatcher("/admins/admin-panel.jsp").forward(req, resp);
       }
   }catch (Exception e){
       resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
   }


    }



}
