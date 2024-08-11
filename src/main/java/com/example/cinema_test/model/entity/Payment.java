package com.example.cinema_test.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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


@Entity(name = "paymentEntity")
@Table(name = "payment_tbl")
public class Payment extends Base {


    @Id
    @SequenceGenerator(name = "paymentSeq", sequenceName = "payment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_price")
    private double price;

    @Column(name="payment_date")
    private LocalDateTime paymentDateTime;

    @Column(name="description" , length = 50)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$")
    private String description;

}
