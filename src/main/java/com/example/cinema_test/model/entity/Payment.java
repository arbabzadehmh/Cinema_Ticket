package com.example.cinema_test.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@SuperBuilder

@Entity(name = "paymentEntity")
@Table(name = "payment_tbl")
public class Payment extends Base{
    @Id
    @SequenceGenerator(name = "paymentSeq", sequenceName = "payment_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentSeq")
    @Column(name = "id", length = 20)
    private Long id;

    @Column(name = "payment_price")
    private float price;

    @Column(name="payment_date")
    private LocalDateTime paymentDateTime;

    @Column(name="description" , length = 50)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$")
    private String description;

}
