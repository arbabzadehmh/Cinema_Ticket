package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
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
@WebServlet(urlPatterns = "/customer.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class CustomerServlet extends HttpServlet {

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
            System.out.println("customer.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("customer.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user == null) {
                resp.sendRedirect("/sign-up.jsp");
                return;
            }

            if (user.getRole().getRole().equals("customer")) {

                Customer customer = customerService.findByUsername(user.getUsername());
                CustomerVO customerVO = new CustomerVO(customer);
                req.getSession().setAttribute("customer", customerVO);
                redirectPath = "/customers/customer-panel.jsp";

            } else if (user.getRole().getRole().equals("moderator") || user.getRole().getRole().equals("admin")) {
                List<Customer> customerList = customerService.findAll();
                List<CustomerVO> customerVOList = new ArrayList<>();
                for (Customer customer : customerList) {
                    CustomerVO customerVO = new CustomerVO(customer);
                    customerVOList.add(customerVO);
                }

                req.getSession().setAttribute("allCustomers", customerVOList);
                redirectPath = "/admins/customers.jsp";
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
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/customer.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/customer.do");
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
                log.info("Customer image changed successfully-ID : " + editingCustomer.getId());
                resp.sendRedirect("/customer.do");


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

                BeanValidator<User> userValidator = new BeanValidator<>();
                if (!userValidator.validate(user).isEmpty()) {
                    String errorMessage = "Invalid User Data!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/sign-up.jsp");
                    return;
                }


                if (userService.findByUsername(req.getParameter("phoneNumber")) != null) {
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
                    log.info("Customer saved successfully : " + customer.getPhoneNumber());
                    resp.sendRedirect("/customer.do");
                } else {
                    String errorMessage = "Invalid Customer Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/sign-up.jsp");
                }
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
        Customer customerAb;
        try {

            customerAb = objectMapper.readValue(req.getInputStream(), Customer.class);
            Customer editingCustomer = (Customer) req.getSession().getAttribute("editingCustomer");
            editingCustomer.setEditing(false);
            customerService.edit(editingCustomer);

            editingCustomer.setName(customerAb.getName());
            editingCustomer.setFamily(customerAb.getFamily());
            editingCustomer.setPhoneNumber(customerAb.getPhoneNumber());
            editingCustomer.setEmail(customerAb.getEmail());

            User user = userService.findByUsername(editingCustomer.getPhoneNumber());
            user.setUsername(customerAb.getPhoneNumber());
            userService.edit(user);

            BeanValidator<Customer> customerValidator = new BeanValidator<>();
            if (customerValidator.validate(editingCustomer).isEmpty()) {
                customerService.edit(editingCustomer);
                log.info("Customer updated successfully : " + editingCustomer.getId());

                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, customerAb);
                out.flush();
            } else {
                log.error("Invalid Customer Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid Customer Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update customer.\"}");
            out.flush();
        }
    }


}
