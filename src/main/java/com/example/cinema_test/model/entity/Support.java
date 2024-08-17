package com.example.cinema_test.model.entity;

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

@Entity(name = "supportEntity")
@Table(name = "support_tbl")

public class Support extends Base{
    @Id
    @SequenceGenerator(name = "supportSeq", sequenceName = "support_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supportSeq")
    @Column(name = "id")
    private Long id;


    @OneToMany
    @JoinColumn(
            name = "message_id",
            foreignKey = @ForeignKey(name = "fk_support_message")
    )
    private Message message;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_support_customer")
    )
    private Customer customer;

    @JoinColumn(
            name = "moderator_id",
            foreignKey = @ForeignKey(name = "fk_support_moderator")
    )
    @OneToOne
    private Moderator moderator;


    @Column(name = "issue_time")
    private LocalDateTime issueTime;

    @Column(name = "solved")
    private boolean solved = false;

}
