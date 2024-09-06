package com.example.cinema_test.controller.servlet;

import com.example.cinema_test.controller.validation.BeanValidator;
import com.example.cinema_test.model.entity.Bank;
import com.example.cinema_test.model.service.BankService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@WebServlet("/bank.do")
public class BankServlet extends HttpServlet {
    @Inject
    private BankService bankService;

    @Override
    public void init() throws ServletException {


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String accountNumber = req.getParameter("accountNumber");
            String branchCode = req.getParameter("branchCode");
            String branchName = req.getParameter("branchName");
            String accountBalance = req.getParameter("accountBalance");
            String status = req.getParameter("status");


            if (name == null || accountNumber == null || accountBalance == null || branchName == null || branchCode == null || status == null) {
                resp.getWriter().write("All Fields Required! ");
                return;
            }


            Bank bank = Bank.builder()
                    .name(name)
                    .branchName(branchName)
                    .branchCode(Long.parseLong(branchCode))
                    .accountBalance(Long.parseLong(accountBalance))
                    .accountNumber(accountNumber)
                    .status(Boolean.parseBoolean(status))
                    .build();

            BeanValidator<Bank> validator = new BeanValidator<>();
            if (validator.validate(bank).isEmpty()) {
                bankService.save(bank);
                resp.getWriter().write("Bank With Id " + bank.getId() + "Created");
                log.info("Bank Saved With This Id : " + bank.getId());
                req.getRequestDispatcher("/bank.jsp").forward(req, resp);
            } else {
                log.error("Cannot Save Bank ");
                throw new Exception("Invalid Bank Data");
            }

        } catch (Exception e) {
            resp.getWriter().write("<h1 style= \"background-color : red;\">" + e.getMessage() + "</h1>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            if (id != null) {
                Bank bank = bankService.findById(Long.parseLong(id));
                if (bank != null) {
                    resp.getWriter().write(bank.toString());
                } else {
                    resp.getWriter().write("Bank With Id" + id + "Not Found");
                }
            } else {
                List<Bank> bankList = bankService.findAll();
                for (Bank bank : bankList) {
                    bankList.add(bank);
                    resp.getWriter().write(bank.toString() + "\n");
                }
            }
                req.getRequestDispatcher("/bank.jsp").forward(req, resp);
            } catch(Exception e){
                resp.getWriter().write("<h1 style= \"background-color : red;\">" + e.getMessage() + "</h1>");
                e.printStackTrace();
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
