package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.service.*;
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
@WebServlet(urlPatterns = "/managers.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ManagerServlet extends HttpServlet {

    @Inject
    private ManagerService managerService;

    @Inject
    private AttachmentService attachmentService;

    @Inject
    private RoleService roleService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("managers.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("managers.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user.getRole().getRole().equals("manager")) {
                ManagerVO managerVO = new ManagerVO(managerService.findByUsername(user.getUsername()));
                req.getSession().setAttribute("manager", managerVO);
                redirectPath = "/managers/manager-main-panel.jsp";
            } else if (user.getRole().getRole().equals("admin") || user.getRole().getRole().equals("moderator")) {
                List<Manager> managerList = managerService.findAll();
                List<ManagerVO> managerVOList = new ArrayList<>();
                for (Manager manager : managerList) {
                    ManagerVO managerVO = new ManagerVO(manager);
                    managerVOList.add(managerVO);
                }
                req.getSession().setAttribute("allManagers", managerVOList);
                redirectPath = "/admins/managers.jsp";
            }


            if (req.getParameter("cancel") != null) {
                Manager editingManager = managerService.findById(Long.parseLong(req.getParameter("cancel")));
                editingManager.setEditing(false);
                managerService.edit(editingManager);
                resp.sendRedirect("/managers.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Manager editingManager = managerService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingManager.isEditing()) {
                    editingManager.setEditing(true);
                    managerService.edit(editingManager);
                    req.getSession().setAttribute("editingManager", editingManager);
                    req.getRequestDispatcher("/managers/manager-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/managers.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/managers.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getPart("newImage") != null) {
                Manager editingManager = (Manager) req.getSession().getAttribute("editingManager");

                for (Attachment attachment : editingManager.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingManager.getAttachments().clear();


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

                editingManager.addAttachment(attachment);
                editingManager.setEditing(false);
                managerService.edit(editingManager);
                log.info("Manager image changed successfully-ID : " + editingManager.getId());
                resp.sendRedirect("/managers.do");


            } else {


                Role role = roleService.findByRole("manager");

                User user =
                        User
                                .builder()
                                .username(req.getParameter("username"))
                                .password(req.getParameter("password"))
                                .role(role)
                                .locked(false)
                                .deleted(false)
                                .build();


                Manager manager =
                        Manager
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

                    manager.addAttachment(attachment);
                }

                BeanValidator<Manager> managerValidator = new BeanValidator<>();
                if (managerValidator.validate(manager).isEmpty()) {
                    managerService.save(manager);
                    log.info("Manager saved successfully : " + manager.getFamily());
                    resp.sendRedirect("/managers.do");
                } else {
                    String errorMessage = "Invalid Manager Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/managers.do");
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/managers.do");
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
        Manager managerAb;
        try {

            managerAb = objectMapper.readValue(req.getInputStream(), Manager.class);
            Manager editingManager = (Manager) req.getSession().getAttribute("editingManager");
            editingManager.setName(managerAb.getName());
            editingManager.setFamily(managerAb.getFamily());
            editingManager.setPhoneNumber(managerAb.getPhoneNumber());
            editingManager.setEmail(managerAb.getEmail());
            editingManager.setNationalCode(managerAb.getNationalCode());
            editingManager.setAddress(managerAb.getAddress());
            editingManager.setEditing(false);
            managerService.edit(editingManager);
            log.info("Manager updated successfully : " + editingManager.getId());


            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, managerAb); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update manager.\"}");
            out.flush();
        }
    }


}
