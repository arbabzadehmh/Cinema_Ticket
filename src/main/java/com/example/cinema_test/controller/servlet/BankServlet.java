package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.exception.ExceptionWrapper;
import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.Bank;
import com.example.cinema_test.model.entity.User;
import com.example.cinema_test.model.service.BankService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(urlPatterns = "/bank.do")
public class BankServlet extends HttpServlet {
    @Inject
    private BankService bankService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            User user = (User) req.getSession().getAttribute("user");

            if (req.getParameter("cancel") != null) {
                Bank editingBank = bankService.findById(Long.parseLong(req.getParameter("cancel")));
                editingBank.setEditing(false);
                bankService.edit(editingBank);
                resp.sendRedirect("/bank.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Bank editingBank = bankService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingBank.isEditing()) {
                    editingBank.setEditing(true);
                    bankService.edit(editingBank);
                    req.getSession().setAttribute("editingBank", editingBank);
                    log.info("Bank Id : " + editingBank.getId() + " is editing by : " + user.getUsername());
                    req.getRequestDispatcher("/banks/bank-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/bank.do");
                }
            } else {
                req.getSession().setAttribute("bankList", bankService.findAll());
                req.getRequestDispatcher("/banks/bank.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/bank.do");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String name = req.getParameter("name").toUpperCase();
            String accountNumber = req.getParameter("accountNumber");
            String branchCode = req.getParameter("branchCode");
            String branchName = req.getParameter("branchName").toUpperCase();
            String accountBalance = req.getParameter("accountBalance");
            boolean status = Boolean.parseBoolean(req.getParameter("status"));


            Bank bank = Bank.builder()
                    .name(name)
                    .branchName(branchName)
                    .branchCode(Long.parseLong(branchCode))
                    .accountBalance(Long.parseLong(accountBalance))
                    .accountNumber(accountNumber)
                    .status(status)
                    .build();

            BeanValidator<Bank> validator = new BeanValidator<>();
            if (validator.validate(bank).isEmpty()) {
                bankService.save(bank);
                log.info("Bank Saved With This Id : " + bank.getId());
                resp.sendRedirect("/bank.do");
            } else {
                String errorMessage = "Invalid Bank Data !!!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/bank.do");
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/bank.do");
        }
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String accountNumber = req.getParameter("accountNumber");
            String branchCode = req.getParameter("branchCode");
            String branchName = req.getParameter("branchName");
            String accountBalance = req.getParameter("accountBalance");
            String status = req.getParameter("status");

            if (id == null) {
                resp.getWriter().write("ID Is Required");
                return;
            }
            Bank bank = bankService.findById(Long.parseLong(id));
            if (bank == null) {
                resp.getWriter().write("Bank With Id" + id + "Not Found");
                return;
            }
            if (name != null) bank.setName(name);
            if (accountBalance != null) bank.setAccountBalance(Long.parseLong(accountBalance));
            if (accountNumber != null) bank.setAccountNumber(accountNumber);
            if (branchCode != null) bank.setBranchCode(Long.parseLong(branchCode));
            if (branchName != null) bank.setBranchName(branchName);
            if (status != null) bank.setStatus(Boolean.parseBoolean(status));

            bankService.edit(bank);
            resp.getWriter().write("Bank With Id" + bank.getId() + "Edited");
        } catch (Exception e) {
            resp.getWriter().write("<h1 style= \"background-color : red;\">" + e.getMessage() + "</h1>");
        }
    }
}
