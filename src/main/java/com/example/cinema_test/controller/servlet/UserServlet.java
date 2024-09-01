package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.service.AttachmentService;
import com.example.cinema_test.model.service.CustomerService;
import com.example.cinema_test.model.service.RoleService;
import com.example.cinema_test.model.service.UserService;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/user.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
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
            System.out.println("user.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("user.do\n\n\n\n");


            User   user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user == null) {
                resp.sendRedirect("/reset-password.jsp");
                return;
            }

            if (user.getRole().getRole().equals("customer")  || user.getRole().getRole().equals("manager")){

//                redirectPath = "/customers/customer-panel.jsp";

            } else if (user.getRole().getRole().equals("moderator") ||  user.getRole().getRole().equals("admin")) {

                req.getSession().setAttribute("allUsers", userService.findAll());
                redirectPath = "/admins/users.jsp";

            }


            if (req.getParameter("cancel") != null) {
                Customer editingCustomer = customerService.findById(Long.parseLong(req.getParameter("cancel")));
                editingCustomer.setEditing(false);
                customerService.edit(editingCustomer);
                resp.sendRedirect("/customer.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Customer editingCustomer = customerService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingCustomer.isEditing()) {
                    editingCustomer.setEditing(true);
                    customerService.edit(editingCustomer);
                    req.getSession().setAttribute("editingCustomer", editingCustomer);
                    req.getRequestDispatcher("/customers/customer-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Record is editing by another user !!!" + "</h1>");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getPart("newImage") != null) {
                Customer editingCustomer = (Customer) req.getSession().getAttribute("editingCustomer");

                for (Attachment attachment : editingCustomer.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingCustomer.getAttachments().clear();


                Part filePart = req.getPart("newImage");

                String applicationPath = req.getServletContext().getRealPath("");

                String uploadDirectory = applicationPath + "uploads";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String fileName = filePart.getSubmittedFileName();
                String filePath = uploadDirectory + File.separator + fileName;
                String relativePath = "/uploads/" + fileName;

                filePart.write(filePath);


                Attachment attachment = Attachment.builder()
                        .attachTime(LocalDateTime.now())
                        .fileName(relativePath)
                        .fileType(FileType.JPG)
                        .fileSize(filePart.getSize())
                        .build();

                editingCustomer.addAttachment(attachment);
                editingCustomer.setEditing(false);
                customerService.edit(editingCustomer);
                resp.sendRedirect("/customer.do");
                log.info("Customer image changed successfully-ID : " + editingCustomer.getId());


            } else {


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

                Part filePart = req.getPart("image");

                if (filePart != null && filePart.getSize() > 0) {
                    String applicationPath = req.getServletContext().getRealPath("");

                    String uploadDirectory = applicationPath + "uploads";
                    File uploadDir = new File(uploadDirectory);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String fileName = filePart.getSubmittedFileName();
                    String filePath = uploadDirectory + File.separator + fileName;
                    String relativePath = "/uploads/" + fileName;

                    filePart.write(filePath);

                    Attachment attachment = Attachment.builder()
                            .attachTime(LocalDateTime.now())
                            .fileName(relativePath)
                            .fileType(FileType.JPG)
                            .fileSize(filePart.getSize())
                            .build();

                    customer.addAttachment(attachment);
                }

                BeanValidator<Customer> customerValidator = new BeanValidator<>();
                if (customerValidator.validate(customer).isEmpty()) {
                    customerService.save(customer);
                    req.getSession().setAttribute("user", user);
                    resp.sendRedirect("/customer.do");
                    log.info("Customer saved successfully : " + customer.getPhoneNumber());
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Invalid Customer Data !!!" + "</h1>");
                }
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
        Customer customerAb;
        try {

            customerAb = objectMapper.readValue(req.getInputStream(), Customer.class);
            Customer editingCustomer = (Customer) req.getSession().getAttribute("editingCustomer");
            editingCustomer.setName(customerAb.getName());
            editingCustomer.setFamily(customerAb.getFamily());
            editingCustomer.setPhoneNumber(customerAb.getPhoneNumber());
            editingCustomer.setEmail(customerAb.getEmail());
            editingCustomer.setEditing(false);

            User user = userService.findByUsername(editingCustomer.getPhoneNumber());
            user.setUsername(customerAb.getPhoneNumber());
            userService.edit(user);

            customerService.edit(editingCustomer);


            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, customerAb); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update customer.\"}");
            out.flush();
        }
    }


}
