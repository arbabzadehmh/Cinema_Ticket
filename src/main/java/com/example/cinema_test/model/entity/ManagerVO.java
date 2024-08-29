package com.example.cinema_test.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


public class ManagerVO {

    private Long id;

    private String name;

    private String family;

    private String phoneNumber;

    private String email;

    private String nationalCode;

    private String address;

    private String cinemaName;

    private User user;

    private String imageUrl;


    public ManagerVO(Manager manager){
        this.id = manager.getId();
        this.name = manager.getName();
        this.family = manager.getFamily();
        this.phoneNumber = manager.getPhoneNumber();
        this.email = manager.getEmail();
        this.nationalCode = manager.getNationalCode();
        this.address = manager.getAddress();
        this.user = manager.getUser();

        if (manager.getCinema() != null){
            this.cinemaName = manager.getCinema().getName();
        }

        if (manager.getAttachments().isEmpty()) {
            this.imageUrl = "";
        } else {
            this.imageUrl = manager.getAttachments().get(0).getFileName();
        }

    }

}
