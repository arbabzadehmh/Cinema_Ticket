package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.service.AttachmentService;
import com.example.cinema_test.model.service.ModeratorService;
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
@WebServlet(urlPatterns = "/moderator.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ModeratorServlet extends HttpServlet {

    @Inject
    private ModeratorService moderatorService;

    @Inject
    private RoleService roleService;

    @Inject
    private AttachmentService attachmentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("moderator.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("moderator.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");

            if (user.getRole().getRole().equals("moderator")) {
                Moderator loggedModerator = moderatorService.findByUsername(user.getUsername());
                req.getSession().setAttribute("loggedModerator", loggedModerator);
            }


            if (req.getParameter("cancel") != null) {
                Moderator editingModerator = moderatorService.findById(Long.parseLong(req.getParameter("cancel")));
                editingModerator.setEditing(false);
                moderatorService.edit(editingModerator);
                resp.sendRedirect("/moderator.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Moderator editingModerator = moderatorService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingModerator.isEditing()) {
                    editingModerator.setEditing(true);
                    moderatorService.edit(editingModerator);
                    req.getSession().setAttribute("editingModerator", editingModerator);
                    req.getRequestDispatcher("/moderators/moderator-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Record is editing by another user !!!" + "</h1>");
                }
            } else {
                req.getSession().setAttribute("allModerators", moderatorService.findAll());
                req.getRequestDispatcher("/moderators/moderator-panel.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getPart("newImage") != null) {
                Moderator editingModerator = (Moderator) req.getSession().getAttribute("editingModerator");

                for (Attachment attachment : editingModerator.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingModerator.getAttachments().clear();


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

                editingModerator.addAttachment(attachment);
                editingModerator.setEditing(false);
                moderatorService.edit(editingModerator);
                resp.sendRedirect("/admins.do");
                log.info("Moderator image changed successfully-ID : " + editingModerator.getId());


            } else {


                Role role = roleService.findByRole("moderator");

                User user =
                        User
                                .builder()
                                .username(req.getParameter("username"))
                                .password(req.getParameter("password"))
                                .role(role)
                                .locked(false)
                                .deleted(false)
                                .build();


                Moderator moderator =
                        Moderator
                                .builder()
                                .name(req.getParameter("name"))
                                .family(req.getParameter("family"))
                                .phoneNumber(req.getParameter("phoneNumber"))
                                .email(req.getParameter("email"))
                                .nationalCode(req.getParameter("nationalCode"))
                                .address(req.getParameter("address"))
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

                    moderator.addAttachment(attachment);
                }

                BeanValidator<Moderator> moderatorValidator = new BeanValidator<>();
                if (moderatorValidator.validate(moderator).isEmpty()) {
                    moderatorService.save(moderator);
                    resp.sendRedirect("/moderator.do");
                    log.info("Moderator saved successfully : " + moderator.getFamily());
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Invalid Moderator Data !!!" + "</h1>");
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
        Moderator moderatorAb;
        try {

            moderatorAb = objectMapper.readValue(req.getInputStream(), Moderator.class);
            Moderator editingModerator = (Moderator) req.getSession().getAttribute("editingModerator");
            editingModerator.setName(moderatorAb.getName());
            editingModerator.setFamily(moderatorAb.getFamily());
            editingModerator.setNationalCode(moderatorAb.getNationalCode());
            editingModerator.setPhoneNumber(moderatorAb.getPhoneNumber());
            editingModerator.setEmail(moderatorAb.getEmail());
            editingModerator.setAddress(moderatorAb.getAddress());
            editingModerator.setEditing(false);
            moderatorService.edit(editingModerator);


            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, moderatorAb); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update admin.\"}");
            out.flush();
        }
    }


}