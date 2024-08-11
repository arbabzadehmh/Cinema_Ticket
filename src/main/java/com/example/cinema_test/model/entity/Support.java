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

    @Column(name = "customer_description", length = 200)
    private String customerDescription;

    @Column(name = "manager_description", length = 200)
    private String managerDescription;

    @Column(name = "moderator_description", length = 200)
    private String moderatorDescription;

    @OneToOne
    @JoinColumn(
            name = "moderator_id",
            foreignKey = @ForeignKey(name = "fk_support_moderator")
    )
    private Moderator moderator;

    @OneToOne
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_support_customer")
    )
    private Customer customer;

    @Column(name = "issue_time")
    private LocalDateTime issueTime;

    @OneToOne
    @JoinColumn(
            name = "manager_id",
            foreignKey = @ForeignKey(name = "fk_support_manager")
    )
    private Manager manager ;

    @Column(name = "solved")
    private boolean solved = false;

}
