package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
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

            if (user.getRole().getRole().equals("customer")) {

                redirectPath = "/customers/customer-user.jsp";

            } else if (user.getRole().getRole().equals("manager")) {

                redirectPath = "/managers/manager-user.jsp";

            } else if (user.getRole().getRole().equals("moderator") || user.getRole().getRole().equals("admin")) {

                req.getSession().setAttribute("allUsers", userService.findAll());
                redirectPath = "/admins/users.jsp";

            }


            if (req.getParameter("cancel") != null) {
                User editingUser = userService.findByUsername(req.getParameter("cancel"));
                editingUser.setEditing(false);
                userService.edit(editingUser);
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
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/users.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/users.do");
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

//            TODO: USER VALIDATION

            if (userService.findByUsername(req.getParameter("phoneNumber")) != null){
                String errorMessage = "Duplicate username(phoneNumber) !!!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/sign-up.jsp");
                return;
            }


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
                log.info("Customer saved successfully : " + customer.getPhoneNumber());
                resp.sendRedirect("/customer.do");
            } else {
                String errorMessage = "Invalid User Data !!!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/sign-up.jsp");
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/sign-up.jsp");
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
            log.info("User updated successfully : " + editingUser.getUsername());

            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, userAb); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update user.\"}");
            out.flush();
        }
    }


}
