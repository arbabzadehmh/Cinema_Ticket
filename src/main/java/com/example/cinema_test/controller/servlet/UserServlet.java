package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.service.AttachmentService;
import com.example.cinema_test.model.service.CustomerService;
import com.example.cinema_test.model.service.RoleService;
import com.example.cinema_test.model.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@Slf4j
@WebServlet(urlPatterns = "/users.do")
public class UserServlet extends HttpServlet {

    @Inject
    private CustomerService customerService;

    @Inject
    private RoleService roleService;

    @Inject
    private AttachmentService attachmentService;

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("users.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("users.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user == null) {
                resp.sendRedirect("/reset-password.jsp");
                return;
            }

            if (user.getRole().getRole().equals("customer") || user.getRole().getRole().equals("manager")) {
                req.getSession().setAttribute("editingUser", user);
                System.out.println(user);
                redirectPath = "/users/user-edit.jsp";

            } else if (user.getRole().getRole().equals("moderator") || user.getRole().getRole().equals("admin")) {

                req.getSession().setAttribute("allUsers", userService.findAll());
                redirectPath = "/admins/users.jsp";

            }


            if (req.getParameter("cancel") != null) {
                User editingUser = userService.findByUsername(req.getParameter("cancel"));
                editingUser.setEditing(false);
                userService.edit(editingUser);
//                if (user.getRole().getRole().equals("customer")) {
//                    resp.sendRedirect("/customer.do");
//                } else if (user.getRole().getRole().equals("moderator")) {
//                    resp.sendRedirect("/moderator.do");
//                } else if (user.getRole().getRole().equals("admin")) {
//                    resp.sendRedirect("/admins.do");
//                } else if (user.getRole().getRole().equals("manager")) {
//                    resp.sendRedirect("/managers.do");
//                }
                resp.sendRedirect("/postLogin.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                User editingUser = userService.findByUsername(req.getParameter("edit"));
                if (!editingUser.isEditing()) {
                    editingUser.setEditing(true);
                    userService.edit(editingUser);
                    req.getSession().setAttribute("editingUser", editingUser);
                    req.getRequestDispatcher("/users/user-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Record is editing by another user !!!" + "</h1>");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Role role = roleService.findByRole("customer");

            User user =
                    User
                            .builder()
                            .username(req.getParameter("phoneNumber"))
                            .password(req.getParameter("password"))
                            .role(role)
                            .locked(false)
                            .deleted(false)
                            .build();


            Customer customer =
                    Customer
                            .builder()
                            .name(req.getParameter("name"))
                            .family(req.getParameter("family"))
                            .phoneNumber(req.getParameter("phoneNumber"))
                            .email(req.getParameter("email"))
                            .user(user)
                            .deleted(false)
                            .build();


            BeanValidator<Customer> customerValidator = new BeanValidator<>();
            if (customerValidator.validate(customer).isEmpty()) {
                customerService.save(customer);
                req.getSession().setAttribute("user", user);
                resp.sendRedirect("/customer.do");
                log.info("Customer saved successfully : " + customer.getPhoneNumber());
            } else {
                resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Invalid Customer Data !!!" + "</h1>");
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
        User userAb;
        try {


            userAb = objectMapper.readValue(req.getInputStream(), User.class);
            User editingUser = (User) req.getSession().getAttribute("editingUser");


            editingUser.setPassword(userAb.getPassword());
            editingUser.setLocked(userAb.isLocked());
            editingUser.setEditing(false);


            userService.edit(editingUser);

            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, userAb); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update user.\"}");
            out.flush();
        }
    }


}
