package com.example.cinema_test.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity(name="seatEntity")
@Table(name="seat_tbl")
public class Seat extends Base {

    @Id
    @SequenceGenerator(name = "seatSeq", sequenceName = "seat_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seatSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "row_number")
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

    public void seatLabelMaker(){
        String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

            this.label = String.valueOf(LETTERS.substring(rowNumber - 1, rowNumber + 1).charAt(0) + this.seatNumber);
    }
}
