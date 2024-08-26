package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.model.entity.Manager;
import com.example.cinema_test.model.entity.User;
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

@WebServlet(urlPatterns = "/managers.do")
public class ManagerServlet extends HttpServlet {

    @Inject
    private ManagerService managerService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("cancel") != null){
                Manager editingManager = managerService.findById(Long.parseLong(req.getParameter("cancel")));
                editingManager.setEditing(false);
                managerService.edit(editingManager);
                resp.sendRedirect("/managers.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Manager editingManager = managerService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingManager.isEditing()){
                    editingManager.setEditing(true);
                    managerService.edit(editingManager);
                    req.getSession().setAttribute("editingManager", editingManager);
                    req.getRequestDispatcher("/managers/manager-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Record is editing by another user !!!" + "</h1>");

                }
            } else {
                User user = (User) req.getSession().getAttribute("user");
                req.getSession().setAttribute("manager", managerService.findByUsername(user.getUsername()));
                req.getRequestDispatcher("/managers/manager-main-panel.jsp").forward(req, resp);
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
        Manager managerVo;
        try {
            managerVo = objectMapper.readValue(req.getInputStream(), Manager.class);
            Manager editingManager = (Manager) req.getSession().getAttribute("editingManager");
            editingManager.setName(managerVo.getName());
            editingManager.setFamily(managerVo.getFamily());
            editingManager.setPhoneNumber(managerVo.getPhoneNumber());
            editingManager.setEmail(managerVo.getEmail());
            editingManager.setNationalCode(managerVo.getNationalCode());
            editingManager.setAddress(managerVo.getAddress());
            editingManager.setEditing(false);
            managerService.edit(editingManager);


            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, managerVo); // Write manager object as JSON response
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


}
