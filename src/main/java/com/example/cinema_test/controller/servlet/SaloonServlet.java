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
@WebServlet(urlPatterns = "/saloon.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class SaloonServlet extends HttpServlet {

    @Inject
    private ManagerService managerService;

    @Inject
    private CinemaService cinemaService;

    @Inject
    private SaloonService saloonService;

    @Inject
    private SeatService seatService;

    @Inject
    private AttachmentService attachmentService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("saloon.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("saloon.do\n\n\n\n");



            User user = (User) req.getSession().getAttribute("user");

            ManagerVO managervo = null;
            Manager manager = null;
            String redirectPath = "";

            if (user.getRole().getRole().equals("manager")) {
                managervo = (ManagerVO) req.getSession().getAttribute("manager");
                manager = managerService.findById(managervo.getId());
                redirectPath = "/managers/manager-saloon.jsp";
            } else if (user.getRole().getRole().equals("admin") || user.getRole().getRole().equals("moderator")) {
                if (req.getParameter("cinemaId") != null) {
                    manager = managerService.findManagerByCinemaId(Long.parseLong(req.getParameter("cinemaId")));
                    managervo = new ManagerVO(manager);
                    req.getSession().setAttribute("manager", managervo);
                } else {
                    managervo = (ManagerVO) req.getSession().getAttribute("manager");
                    manager = managerService.findById(managervo.getId());
                }
                redirectPath = "/cinemas/cinema-saloon.jsp";
            }

            List<Saloon> cinemaSaloons = manager.getCinema().getSaloonList();


            if (req.getParameter("cancel") != null) {
                Saloon editingSaloon = saloonService.findById(Long.parseLong(req.getParameter("cancel")));
                editingSaloon.setEditing(false);
                saloonService.edit(editingSaloon);
                resp.sendRedirect("/saloon.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Saloon editingSaloon = saloonService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingSaloon.isEditing()) {
                    editingSaloon.setEditing(true);
                    saloonService.edit(editingSaloon);
                    req.getSession().setAttribute("editingSaloon", editingSaloon);
                    req.getRequestDispatcher("/saloons/saloon-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/saloon.do");
                }
            } else {
                req.getSession().setAttribute("cinemaSaloons", cinemaSaloons);
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/saloon.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Part filePart = req.getPart("newImage");
            if (filePart != null && filePart.getSize() > 0) {

                Saloon editingSaloon = (Saloon) req.getSession().getAttribute("editingSaloon");


                for (Attachment attachment : editingSaloon.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingSaloon.getAttachments().clear();


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

                // Create and attach the new attachment
                Attachment attachment = Attachment.builder()
                        .attachTime(LocalDateTime.now())
                        .fileName(relativePath)
                        .fileType(FileType.JPG)
                        .fileSize(filePart.getSize())
                        .build();

                editingSaloon.addAttachment(attachment);
                editingSaloon.setEditing(false);
                saloonService.edit(editingSaloon);
                log.info("Saloon image changed successfully-ID : " + editingSaloon.getId());
                resp.sendRedirect("/saloon.do");

            } else {

                ManagerVO managervo = (ManagerVO) req.getSession().getAttribute("manager");
                Manager manager = managerService.findById(managervo.getId());

                Saloon saloon = Saloon.builder()
                        .saloonNumber(Integer.parseInt(req.getParameter("saloonNumber")))
                        .capacity(0)
                        .saloonRow(Integer.parseInt(req.getParameter("saloonRow")))
                        .saloonColumn(Integer.parseInt(req.getParameter("saloonColumn")))
                        .status(Boolean.parseBoolean(req.getParameter("status")))
                        .description(req.getParameter("description"))
                        .deleted(false)
                        .build();

                saloon.setCapacity(saloon.getSaloonColumn() * saloon.getSaloonRow());

                double ratio;
                List<Seat> seats = new ArrayList<>();
                for (int i = 1; i <= saloon.getSaloonRow(); i++) {
                    if (i <= 2) {
                        ratio = 2;
                    } else if (i > 2 && i < 5) {
                        ratio = 1.5;
                    } else {
                        ratio = 1;
                    }
                    for (int j = 1; j <= saloon.getSaloonColumn(); j++) {
                        Seat seat = Seat.builder()
                                .rowNumber(i)
                                .seatNumber(j)
                                .label(null)
                                .priceRatio(ratio)
                                .status(true)
                                .deleted(false)
                                .description("")
                                .build();

                        seat.seatLabelMaker();
                        seats.add(seat);
                        seatService.save(seat);
                    }
                }

                saloon.setSeats(seats);

                Part imagePart = req.getPart("image");
                if (imagePart != null && imagePart.getSize() > 0) {
                    // Handle image upload for new saloon creation
                    String uploadDirPath = req.getServletContext().getRealPath("") + "uploads";
                    File dir = new File(uploadDirPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    String imageName = imagePart.getSubmittedFileName();
                    String imagePath = uploadDirPath + File.separator + imageName;
                    String relativeImagePath = "/uploads/" + imageName;
                    imagePart.write(imagePath);

                    Attachment attachment = Attachment.builder()
                            .attachTime(LocalDateTime.now())
                            .fileName(relativeImagePath)
                            .fileType(FileType.JPG)
                            .fileSize(imagePart.getSize())
                            .build();

                    saloon.addAttachment(attachment);
                }

                BeanValidator<Saloon> saloonValidator = new BeanValidator<>();
                if (saloonValidator.validate(saloon).isEmpty()) {
                    saloonService.save(saloon);
                    Cinema cinema = manager.getCinema();
                    cinema.addSaloon(saloon);
                    cinemaService.edit(cinema);
                    req.getSession().setAttribute("saloon", saloon);
                    log.info("Saloon saved successfully-ID : " + saloon.getId());
                    resp.sendRedirect("/saloon.do");
                } else {
                    String errorMessage = "Invalid Saloon Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/saloon.do");                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/saloon.do");
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Saloon saloonAb;

        try {
            saloonAb = objectMapper.readValue(req.getInputStream(), Saloon.class);
            Saloon editingSaloon = (Saloon) req.getSession().getAttribute("editingSaloon");
            editingSaloon.setSaloonNumber(saloonAb.getSaloonNumber());
            editingSaloon.setStatus(saloonAb.isStatus());
            editingSaloon.setDescription(saloonAb.getDescription());
            editingSaloon.setEditing(false);
            editingSaloon.setCapacity(saloonService.findSaloonSeats(editingSaloon.getId()).size());
            saloonService.edit(editingSaloon);
            log.info("Saloon edited successfully-ID : " + editingSaloon.getId());

            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, saloonAb); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update saloon.\"}");
            out.flush();
        }

    }


}
