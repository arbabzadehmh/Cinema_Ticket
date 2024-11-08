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


public class CinemaVO {

    private Long id;

    private String name;

    private String address;

    private boolean status;

    private String description;

    private String imageUrl;


    public CinemaVO(Cinema cinema) {
        this.id = cinema.getId();
        this.name = cinema.getName();
        this.address = cinema.getAddress();
        this.status = cinema.isStatus();
        this.description = cinema.getDescription();

        if (cinema.getAttachments().isEmpty()) {
            this.imageUrl = "";
        } else {
            this.imageUrl = cinema.getAttachments().get(0).getFileName();
        }
    }

}
