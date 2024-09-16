package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.model.entity.*;
import com.example.cinema_test.model.entity.enums.FileType;
import com.example.cinema_test.model.service.MessageService;
import com.example.cinema_test.model.service.SupportService;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Slf4j
@WebServlet(urlPatterns = "/message.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class MessageServlet extends HttpServlet {

    @Inject
    private MessageService messageService;


    @Inject
    private SupportService supportService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("message.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("message.do\n\n\n\n");


            Support openingSupport = (Support) req.getSession().getAttribute("openingSupport");
            List<Message> sortedMessageList = new ArrayList<>();
            for (Message message : openingSupport.getMessageList()) {
                message.setSendTime(message.getSendTime().truncatedTo(ChronoUnit.MINUTES));
                sortedMessageList.add(message);
            }
            sortedMessageList.sort(Comparator.comparing(Message::getSendTime));
            req.getSession().setAttribute("sortedMessageList", sortedMessageList);
            req.getRequestDispatcher("/messages/message.jsp").forward(req, resp);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/message.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            User user = (User) req.getSession().getAttribute("user");
            Support support = (Support) req.getSession().getAttribute("openingSupport");
            String sender = "moderator";
            if (user.getRole().getRole().equals("customer")) {
                sender = "customer";
            }

            Message message =
                    Message
                            .builder()
                            .text(req.getParameter("text"))
                            .sendTime(LocalDateTime.now())
                            .sender(sender)
                            .support(support)
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

                message.addAttachment(attachment);
            }

            messageService.save(message);
            support.addMessage(message);
            supportService.edit(support);
            log.info("Message saved successfully : " + message.getId());
            resp.sendRedirect("/message.do");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/support.do");
        }
    }


}