package com.example.cinema_test.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity(name = "ticketEntity")
@Table(name = "ticket_tbl")
public class Ticket extends Base {

    @Id
    @SequenceGenerator(name = "ticketSeq", sequenceName = "ticket_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticketSeq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
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

    @Column(name = "price")
    private double price;

    @Column(name = "issue_time")
    private LocalDateTime issueTime;

    @Column(name = "seat_id")
    private Long seatId;

    @Column(name = "reserved")
    private boolean reserved;

    @ManyToOne
    @JoinColumn(name = "payment_id",
            foreignKey = @ForeignKey(name = "fk_ticket_payment")
    )
    private Payment payment;

    @OneToMany(mappedBy = "ticket", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;


    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setTicket(this);
    }


}
