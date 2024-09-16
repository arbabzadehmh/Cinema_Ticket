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

@Entity(name = "supportEntity")
@Table(name = "support_tbl")

public class    Support extends Base{
    @Id
    @SequenceGenerator(name = "supportSeq", sequenceName = "support_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supportSeq")
    @Column(name = "id")
    private Long id;


    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "support_message_tbl",
            joinColumns = @JoinColumn(name = "support_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id"),
            foreignKey = @ForeignKey(name = "fk_support_message"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_support_message")
    )
    private List<Message> messageList;


    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_support_customer")
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "moderator_id",
            foreignKey = @ForeignKey(name = "fk_support_moderator")
    )
    private Moderator moderator;


    @Column(name = "issue_time")
    private LocalDateTime issueTime;

    @Column(name = "solved")
    private boolean solved = false;

    public void addMessage(Message message) {
        if (messageList == null) {
            messageList = new ArrayList<>();
        }
        messageList.add(message);
    }


}
