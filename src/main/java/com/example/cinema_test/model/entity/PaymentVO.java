package com.example.cinema_test.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


public class PaymentVO {

    private Long id;

    private String customerPhoneNumber;

    private double price;

    private String paymentDateTime;

    private String description;

    private String bankName;

    private String accountNumber;

    private List<Long> ticketIds = new ArrayList<>();

    private int ticketCounts;

    private String imageUrl;


    public PaymentVO(Payment payment){
        this.id = payment.getId();
        this.price = payment.getPrice();
        this.paymentDateTime = payment.getPaymentDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.description = payment.getDescription();
        this.bankName = payment.getBank().getName();
        this.accountNumber = payment.getBank().getAccountNumber();
        this.customerPhoneNumber = payment.getTicketList().get(0).getCustomer().getPhoneNumber();

        for (Ticket ticket : payment.getTicketList()){
            this.ticketIds.add(ticket.getId());
        }

        this.ticketCounts = ticketIds.size();

        if (payment.getAttachments().isEmpty()) {
            this.imageUrl = "";
        } else {
            this.imageUrl = payment.getAttachments().get(0).getFileName();
        }

    }

}
