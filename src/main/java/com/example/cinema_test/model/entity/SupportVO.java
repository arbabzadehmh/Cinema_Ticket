package com.example.cinema_test.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


public class SupportVO {

    private Long id;

    private String customerName;

    private String customerFamily;

    private String customerPhoneNumber;

    private String moderatorName;

    private String moderatorFamily;

    private String issueTime;

    private boolean solved;


    public SupportVO(Support support){
        this.id = support.getId();
        this.customerName = support.getCustomer().getName();
        this.customerFamily = support.getCustomer().getFamily();
        this.customerPhoneNumber = support.getCustomer().getPhoneNumber();
        this.moderatorName = support.getModerator().getName();
        this.moderatorFamily = support.getModerator().getFamily();
        this.issueTime = support.getIssueTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.solved = support.isSolved();
    }

}
