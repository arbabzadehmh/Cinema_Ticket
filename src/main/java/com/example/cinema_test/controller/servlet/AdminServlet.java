package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.service.AdminService;
import com.example.cinema_test.model.service.AttachmentService;
import com.example.cinema_test.model.service.RoleService;
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
import java.util.Enumeration;

@Slf4j
@WebServlet(urlPatterns = "/admins.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AdminServlet extends HttpServlet {

    @Inject
    private AdminService adminService;

    @Inject
    private RoleService roleService;

    @Inject
    private AttachmentService attachmentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("admins.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("admins.do\n\n\n\n");

            User user = (User) req.getSession().getAttribute("user");
            Admin loggedAdmin = adminService.findByUsername(user.getUsername());
            req.getSession().setAttribute("loggedAdmin", loggedAdmin);

            if (req.getParameter("cancel") != null) {
                Admin editingAdmin = adminService.findById(Long.parseLong(req.getParameter("cancel")));
                editingAdmin.setEditing(false);
                adminService.edit(editingAdmin);
                resp.sendRedirect("/admins.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Admin editingAdmin = adminService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingAdmin.isEditing()) {
                    editingAdmin.setEditing(true);
                    adminService.edit(editingAdmin);
                    req.getSession().setAttribute("editingAdmin", editingAdmin);
                    req.getRequestDispatcher("/admins/admin-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/admins.do");
                }
            } else {
                req.getSession().setAttribute("allAdmins", adminService.findAll());
                req.getRequestDispatcher("/admins/admin-panel.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/admins.do");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getPart("newImage") != null) {
                Admin editingAdmin = (Admin) req.getSession().getAttribute("editingAdmin");

                for (Attachment attachment : editingAdmin.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingAdmin.getAttachments().clear();


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

                editingAdmin.addAttachment(attachment);
                editingAdmin.setEditing(false);
                adminService.edit(editingAdmin);
                log.info("Admin image changed successfully-ID : " + editingAdmin.getId());
                resp.sendRedirect("/admins.do");


            } else {


                Role role = roleService.findByRole("admin");

                User user =
                        User
                                .builder()
                                .username(req.getParameter("username"))
                                .password(req.getParameter("password"))
                                .role(role)
                                .locked(false)
                                .deleted(false)
                                .build();


                Admin admin =
                        Admin
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

                    admin.addAttachment(attachment);
                }

                BeanValidator<Admin> adminValidator = new BeanValidator<>();
                if (adminValidator.validate(admin).isEmpty()) {
                    adminService.save(admin);
                    log.info("Admin saved successfully : " + admin.getFamily());
                    resp.sendRedirect("/admins.do");
                } else {
                    String errorMessage = "Invalid Admin Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/admins.do");
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/admins.do");
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
        Admin adminAb;

        try {

            adminAb = objectMapper.readValue(req.getInputStream(), Admin.class);
            Admin editingAdmin = (Admin) req.getSession().getAttribute("editingAdmin");
            editingAdmin.setEditing(false);
            adminService.edit(editingAdmin);

            editingAdmin.setName(adminAb.getName());
            editingAdmin.setFamily(adminAb.getFamily());
            editingAdmin.setPhoneNumber(adminAb.getPhoneNumber());
            editingAdmin.setEmail(adminAb.getEmail());

            BeanValidator<Admin> adminValidator = new BeanValidator<>();
            if (adminValidator.validate(editingAdmin).isEmpty()) {

                adminService.edit(editingAdmin);
                log.info("Admin updated successfully : " + editingAdmin.getId());

                // Send success response with updated admin
                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, adminAb); // Write admin object as JSON response
                out.flush();
            } else {
                log.error("Invalid Admin Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid Admin Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update admin.\"}");
            out.flush();
        }
    }

}
