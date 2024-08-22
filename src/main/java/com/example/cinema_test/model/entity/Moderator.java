package com.example.cinema_test.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity(name = "moderatorEntity")
@Table(name = "moderator_tbl")
public class Moderator extends Base{

    @Id
    @SequenceGenerator(name = "managerSeq", sequenceName = "moderator_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moderatorSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "family", length = 30, nullable = false)
    private String family;

    @Column(name = "phone_number", length = 15, unique = true)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "national_code", length = 10)
    private String nationalCode;

    @Column(name = "address", length = 100)
    private String address;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "username",
            foreignKey = @ForeignKey(name = "fk_moderator_user")
    )
    private User user;

    @OneToMany(mappedBy = "moderator", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Attachment> attachments;


    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
    }
}
