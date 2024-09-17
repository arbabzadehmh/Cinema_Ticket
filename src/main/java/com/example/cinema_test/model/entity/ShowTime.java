package com.example.cinema_test.model.entity;


import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
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


@Entity(name="showTimeEntity")
@Table(name="show_time_tbl")
public class ShowTime extends Base {

    @Id
    @SequenceGenerator(name = "showTimeSeq", sequenceName = "show_time_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "showTimeSeq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "saloon_id",
            foreignKey = @ForeignKey(name = "fk_show_time_saloon")
    )
    private Saloon saloon;

    @ManyToOne
    @JoinColumn(
            name = "show_id",
            foreignKey = @ForeignKey(name = "fk_show_time_show")
    )
    private Show show;

    @Column(name = "remaining_capacity")
    private int remainingCapacity;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;


    @Column(name = "status")
    private boolean status;

    @Column(name = "description", length = 50)
    private String description;

    @JsonbTransient
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(
            name = "cinema_id",
            foreignKey = @ForeignKey(name = "fk_show_time_cinema")
    )
    private Cinema cinema;


}


