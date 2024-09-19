package com.example.cinema_test.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


public class TicketVO {

    private Long id;

    private String customerName;

    private String customerFamily;

    private String customerPhoneNumber;

    private String issueTime;

    private LocalDate showDate;

    private LocalTime startHour;

    private LocalTime endHour;

    private double price;

    private String seatLabel;

    private int saloonNumber;

    private String cinemaName;

    private String address;

    private String showName;

    private String description;

    private boolean verified = false;

    private String imageUrl;

    private String qrCode;


    public TicketVO(Ticket ticket){
        this.id = ticket.getId();
        this.customerName = ticket.getCustomer().getName();
        this.customerFamily = ticket.getCustomer().getFamily();
        this.customerPhoneNumber = ticket.getCustomer().getPhoneNumber();
        this.issueTime = ticket.getIssueTime().toLocalDate().toString() + "      " + ticket.getIssueTime().getHour() + ":" + ticket.getIssueTime().getMinute();
        this.showDate = ticket.getShowTime().getStartTime().toLocalDate();
        this.startHour = ticket.getShowTime().getStartTime().toLocalTime();
        this.endHour = ticket.getShowTime().getEndTime().toLocalTime();
        this.price = ticket.getPrice();
        this.saloonNumber = ticket.getShowTime().getSaloon().getSaloonNumber();
        this.cinemaName = ticket.getShowTime().getCinema().getName();
        this.address = ticket.getShowTime().getCinema().getAddress();
        this.showName = ticket.getShowTime().getShow().getName();

        String showDescription = ticket.getShowTime().getShow().getDescription();
        if (showDescription.length() > 50){
            showDescription = showDescription.substring(0, 50) + "...";
        }
        this.description = ticket.getShowTime().getDescription() + showDescription;

        if (ticket.getAttachments().isEmpty()) {
            this.qrCode = "";
        } else {
            this.qrCode = ticket.getAttachments().get(0).getFileName();
        }

        if (ticket.getShowTime().getShow().getAttachments().isEmpty()){
            this.imageUrl = "";
        } else {
            this.imageUrl = ticket.getShowTime().getShow().getAttachments().get(0).getFileName();
        }


        if (ticket.getPayment() != null){
            this.verified = true;
        }

    }

}
