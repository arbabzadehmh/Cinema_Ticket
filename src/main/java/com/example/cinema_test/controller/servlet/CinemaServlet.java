package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.*;

import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.service.AttachmentService;
import com.example.cinema_test.model.service.CinemaService;
import com.example.cinema_test.model.service.ManagerService;
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

@Slf4j
@WebServlet(urlPatterns = "/cinema.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class CinemaServlet extends HttpServlet {

    @Inject
    private ManagerService managerService;

    @Inject
    private CinemaService cinemaService;

    @Inject
    private AttachmentService attachmentService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user.getRole().getRole().equals("manager")) {
                ManagerVO managerVO = (ManagerVO) req.getSession().getAttribute("manager");
                Manager manager = managerService.findById(managerVO.getId());
                req.getSession().setAttribute("cinema", managerService.findCinemaByManagerId(manager.getId()));
                redirectPath = "/managers/manager-cinema.jsp";
            } else if (user.getRole().getRole().equals("admin") || user.getRole().getRole().equals("moderator")) {
                req.getSession().setAttribute("allCinemas", cinemaService.findAll());
                redirectPath = "/cinemas/cinema.jsp";
            }

            if (req.getParameter("cancel") != null) {
                Cinema editingCinema = cinemaService.findById(Long.parseLong(req.getParameter("cancel")));
                editingCinema.setEditing(false);
                cinemaService.edit(editingCinema);
                resp.sendRedirect("/cinema.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Cinema editingCinema = cinemaService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingCinema.isEditing()) {
                    editingCinema.setEditing(true);
                    cinemaService.edit(editingCinema);
                    req.getSession().setAttribute("editingCinema", editingCinema);
                    req.getRequestDispatcher("/managers/manager-cinema-edit.jsp").forward(req, resp);
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

            if (req.getPart("newImage") != null) {
                Cinema editingCinema = (Cinema) req.getSession().getAttribute("editingCinema");

                for (Attachment attachment : editingCinema.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingCinema.getAttachments().clear();


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

                editingCinema.addAttachment(attachment);
                editingCinema.setEditing(false);
                cinemaService.edit(editingCinema);
                resp.sendRedirect("/cinema.do");
                log.info("Cinema image changed successfully-ID : " + editingCinema.getId());

            } else {

                Cinema cinema =
                        Cinema
                                .builder()
                                .name(req.getParameter("name"))
                                .status(Boolean.parseBoolean(req.getParameter("status")))
                                .description(req.getParameter("description"))
                                .address(req.getParameter("address"))
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

                    cinema.addAttachment(attachment);
                }

                BeanValidator<Cinema> cinemaValidator = new BeanValidator<>();

                if (cinemaValidator.validate(cinema).isEmpty()) {
                    Manager manager = managerService.findById(Long.parseLong(req.getParameter("managerId")));
                    manager.setCinema(cinema);
                    managerService.edit(manager);
                    cinemaService.save(cinema);
                    resp.sendRedirect("/cinema.do");
                    log.info("Cinema saved successfully-ID : " + cinema.getId());
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + "Invalid Cinema Data !!!" + "</h1>");

                }
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: yellow;\">" + e.getMessage() + "</h1>");
            log.error(ExceptionWrapper.getMessage(e).toString());

        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Cinema cinemaAb;

        try {
            cinemaAb = objectMapper.readValue(req.getInputStream(), Cinema.class);
            Cinema editingCinema = (Cinema) req.getSession().getAttribute("editingCinema");
            editingCinema.setName(cinemaAb.getName().toUpperCase());
            editingCinema.setStatus(cinemaAb.isStatus());
            editingCinema.setDescription(cinemaAb.getDescription());
            editingCinema.setAddress(cinemaAb.getAddress());
            editingCinema.setEditing(false);
            cinemaService.edit(editingCinema);


            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, cinemaAb); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update cinema.\"}");
            out.flush();
        }

    }


}