package com.example.cinema_test.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

public class ShowTimeVo {

    private Long id;

    private int saloonNumber;

    private String showName;

    private int remainingCapacity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean status;

    private String description;


    public ShowTimeVo(ShowTime showTime){

        this.id = showTime.getId();
        this.saloonNumber = showTime.getSaloon().getSaloonNumber();
        this.showName = showTime.getShow().getName();
        this.remainingCapacity = showTime.getRemainingCapacity();
        this.startTime = showTime.getStartTime();
        this.endTime = showTime.getEndTime();
        this.status = showTime.isStatus();
        this.description = showTime.getDescription();

    }

}
