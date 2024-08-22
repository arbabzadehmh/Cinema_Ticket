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

    private String description;

    private List<Attachment> attachments;


    public CinemaVO(Cinema cinema){
        this.id = cinema.getId();
        this.name = cinema.getName();
        this.address = cinema.getAddress();
        this.description = cinema.getDescription();
        this.attachments = cinema.getAttachments();
    }

}
