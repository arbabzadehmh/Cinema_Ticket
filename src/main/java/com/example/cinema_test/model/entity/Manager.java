package com.example.cinema_test.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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


@Entity(name = "managerEntity")
@Table(name = "manager_tbl")
public class Manager extends Base {

    @Id
    @SequenceGenerator(name = "managerSeq", sequenceName = "manager_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "managerSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    @Pattern(regexp = "^[a-zA-z\\s]{2,30}$", message = "invalid Name")
    private String name;

    @Column(name = "family", length = 30, nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$" ,message = "Invalid Family")
    private String family;

    @Column(name = "phone_number", length = 15, unique = true)
    @Pattern(regexp = "^(09|\\+989)\\d{9}$" ,message = "Invalid PhoneNumber")
    private String phoneNumber;

    @Column(name = "email", length = 50)
    @Pattern(regexp = "^\\w{0,35}@(gmail|yahoo|microsoft)\\.com$" ,message = "Invalid Email")
    private String email;

    @Column(name = "national_code", length = 10)
    @Pattern(regexp = "^\\d{10}$" ,message = "Invalid National Code")
    private String nationalCode;

    @Column(name = "address", length = 100)
    @Pattern(regexp = "^[\\w\\s]{2,100}$", message = "invalid Address")
    private String address;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "username",

            foreignKey = @ForeignKey(name = "fk_manager_user")
    )
    private User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "cinema_id",
            foreignKey = @ForeignKey(name = "fk_manager_cinema")
    )
    private Cinema cinema;

    @OneToMany(mappedBy = "manager", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;


    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setManager(this);
    }


}
