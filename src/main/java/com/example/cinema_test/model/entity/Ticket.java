package com.example.cinema_test.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity(name="ticketEntity")
@Table(name="ticket_tbl")
public class Ticket extends Base {

    @Id
    @SequenceGenerator(name = "ticketSeq", sequenceName = "ticket_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticketSeq")
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_ticket_customer")
    )
    private Customer customer;

    @OneToOne
    @JoinColumn(
            name = "show_time_id",
            foreignKey = @ForeignKey(name = "fk_ticket_show_time")
    )
    private ShowTime showTime;

    private double price;

    private LocalDateTime issueTime;

    @OneToOne
    @JoinColumn(
            name = "manager_id",
            foreignKey = @ForeignKey(name = "fk_ticket_manager")
    )
    private Manager manager;

}
