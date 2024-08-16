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

@Entity(name = "messageEntity")
@Table(name = "message_tbl")
public class Message extends Base {
    @Id
    @SequenceGenerator(name = "messageSeq", sequenceName = "message_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "text", length = 200)
    private String text;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @Column(name = "sender", length = 50)
    private String sender;


    @ManyToOne
    @JoinColumn(
            name = "support_id",
            foreignKey = @ForeignKey(name = "fk_message_support")
    )
    private Support support;
}
