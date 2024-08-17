package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.Manager;
import com.example.cinema_test.model.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/manager.do")
public class ManagerServlet extends HttpServlet {

    @Inject
    private SeatService seatService;

    @Inject
    private SaloonService saloonService;

    @Inject
    private ShowService showService;

    @Inject
    private ShowTimeService showTimeService;

    @Inject
    private ManagerService managerService;

    @Inject
    private CinemaService cinemaService;

    @Inject
    private RoleService roleService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            if (req.getParameter("edit") != null) {
                Manager editingManager = managerService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingManager.isEditing()){
                    editingManager.setEditing(true);
                    managerService.edit(editingManager);
                    req.getSession().setAttribute("editingManager", editingManager);
                    req.getRequestDispatcher("/managers/manager-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: green;\">" + "Record is editing by another user !!!" + "</h1>");

                }
            } else {
                req.getSession().setAttribute("manager", managerService.findById(1L));
                req.getRequestDispatcher("/managers/manager-main-panel.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            e.printStackTrace();
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
        Manager manager;
        try {
            manager = objectMapper.readValue(req.getInputStream(), Manager.class);
            System.out.println(manager);

            // Perform your business logic here (e.g., update the manager in the database)
            // For now, we assume the update is successful and return the same object

            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, manager); // Write manager object as JSON response
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update manager.\"}");
            out.flush();
        }
    }








//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Manager manager = objectMapper.readValue(req.getInputStream(), Manager.class);
//            System.out.println(manager);
//            manager.setEditing(false);
//            managerService.edit(manager);
//        } catch (Exception e){
//            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
//        }
//
//        req.getRequestDispatcher("/manager.do").forward(req, resp);
//    }




}
