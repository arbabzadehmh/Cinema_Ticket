package com.example.cinema_test.controller.servlet;


import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.Attachment;
import com.example.cinema_test.model.entity.Cinema;
import com.example.cinema_test.model.entity.Manager;
import com.example.cinema_test.model.entity.Show;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.entity.enums.Genre;
import com.example.cinema_test.model.entity.enums.ShowType;
import com.example.cinema_test.model.service.CinemaService;
import com.example.cinema_test.model.service.ManagerService;
import com.example.cinema_test.model.service.ShowService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;



@Slf4j
@WebServlet(urlPatterns = "/managers/show.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ManagerShowServlet extends HttpServlet {

    @Inject
    private ManagerService managerService;

    @Inject
    private CinemaService cinemaService;

    @Inject
    private ShowService showService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {

            if (req.getParameter("cancel") != null){
                Show editingShow = showService.findById(Long.parseLong(req.getParameter("cancel")));
                editingShow.setEditing(false);
                showService.edit(editingShow);
                resp.sendRedirect("/managers/show.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Show editingShow = showService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingShow.isEditing()){
                    editingShow.setEditing(true);
                    showService.edit(editingShow);
                    req.getSession().setAttribute("editingShow", editingShow);
                    req.getRequestDispatcher("/managers/manager-show-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("<h1 style=\"background-color: green;\">" + "Record is editing by another user !!!" + "</h1>");
                }
            } else {
                Manager manager = (Manager) req.getSession().getAttribute("manager");
                req.getSession().setAttribute("shows", managerService.findShowsByManagerId(manager.getId()));
                req.getRequestDispatcher("/managers/manager-show.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
            log.error(ExceptionWrapper.getMessage(e).toString());
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Show show =
                    Show
                            .builder()
                            .name(req.getParameter("name"))
                            .genre(Genre.valueOf(req.getParameter("genre")))
                            .director(req.getParameter("director"))
                            .producer(req.getParameter("producer"))
                            .singer(req.getParameter("singer"))
                            .speaker(req.getParameter("speaker"))
                            .basePrice(Double.parseDouble(req.getParameter("basePrice")))
                            .deleted(false)
                            .available(false)
                            .showType(ShowType.valueOf(req.getParameter("showType")))
                            .status(Boolean.parseBoolean((req.getParameter("status"))))
                            .releasedDate(LocalDate.parse(req.getParameter("releasedDate")))
                            .description(req.getParameter("description"))
                            .build();


            // Handle the file upload part
            Part filePart = req.getPart("image");

            if (filePart != null && filePart.getSize() > 0) { // Check if file is uploaded
                // Get the application's absolute path
                String applicationPath = req.getServletContext().getRealPath("");
                System.out.println(applicationPath);

                // Define the path where the file will be saved
                String uploadDirectory = applicationPath + "uploads";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir(); // Create the uploads directory if it doesn't exist
                }

                // Generate a unique file name (to avoid collisions)
                String fileName = filePart.getSubmittedFileName();
                String filePath = uploadDirectory + File.separator + fileName;
                String relativePath = "/uploads/" + fileName; // Path for displaying in JSP

                // Save the file to the specified directory
                filePart.write(filePath);

                // Create and add the attachment
                Attachment attachment = Attachment.builder()
                        .attachTime(LocalDateTime.now())
                        .fileName(relativePath) // Store the relative path for JSP display
                        .fileType(FileType.JPG) // Assuming JPG, modify as needed
                        .fileSize(filePart.getSize())
                        .build();

                show.addAttachment(attachment);
            }

            BeanValidator<Show> showValidator = new BeanValidator<>();

            if (showValidator.validate(show).isEmpty()) {
                showService.save(show);
                Cinema cinema = (Cinema) req.getSession().getAttribute("cinema");
                cinema.addShow(show);
                cinemaService.edit(cinema);
                req.getSession().setAttribute("cinema", cinema);
                resp.sendRedirect("/managers/show.do");
                log.info("Show saved successfully-ID : " + show.getId());
            } else {
                throw new Exception("Invalid Show Data !!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ExceptionWrapper.getMessage(e).toString());
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Show showVo;

        try {
            showVo = objectMapper.readValue(req.getInputStream(), Show.class);
            Show editingShow = (Show) req.getSession().getAttribute("editingShow");
            editingShow.setName(showVo.getName());
            editingShow.setDirector(showVo.getDirector());
            editingShow.setProducer(showVo.getProducer());
            editingShow.setSinger(showVo.getSinger());
            editingShow.setSpeaker(showVo.getSpeaker());
            editingShow.setReleasedDate(showVo.getReleasedDate());
            editingShow.setStatus(showVo.isStatus());
            editingShow.setShowType(showVo.getShowType());
            editingShow.setGenre(showVo.getGenre());
            editingShow.setDescription(showVo.getDescription());
            editingShow.setEditing(false);
            showService.edit(editingShow);

            log.info("Show edited successfully : " + editingShow.toString());

            // Send success response with updated manager
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, showVo); // Write manager object as JSON response
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            // Send error response if something goes wrong
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update show.\"}");
            out.flush();
        }

    }



}