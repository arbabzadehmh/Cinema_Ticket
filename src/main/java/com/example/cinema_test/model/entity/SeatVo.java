package com.example.cinema_test.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity(name="seatVoEntity")
@Table(name="seatVo_tbl")

public class SeatVo extends Base {

    @Id
    @SequenceGenerator(name = "seatVoSeq", sequenceName = "seat_vo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seatVoSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "row_number" )
    private int rowNumber;

    @Column(name = "seat_number")
    private int seatNumber;

    @Column(name = "label", length = 3)
    private String label;

    @Column(name = "price_ratio")
    private double priceRatio;

    @Column(name = "status")
    private boolean status;

    @Column(name = "description")
    private String description;


    public SeatVo(Seat seat) {

        this.seatNumber = seat.getSeatNumber();
        this.rowNumber = seat.getRowNumber();
        this.label = seat.getLabel();
        this.priceRatio = seat.getPriceRatio();
        this.status = seat.isStatus();
        this.description = seat.getDescription();
    }
}
