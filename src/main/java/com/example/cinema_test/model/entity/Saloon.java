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


@Entity(name="saloonEntity")
@Table(name="saloon_tbl")
public class Saloon extends Base {

    @Id
    @SequenceGenerator(name = "saloonSeq", sequenceName = "saloon_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "saloonSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "saloon_number", nullable = false )
    private int saloonNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "saloon_row")
    private int saloonRow;

    @Column(name = "saloon_column")
    private int saloonColumn;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "saloon_seat_tbl",
            joinColumns = @JoinColumn(name = "saloon_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id"),
            foreignKey = @ForeignKey(name = "fk_saloon_seat"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_saloon_seat")
    )
    private List<Seat> seats;

    @Column(name = "status")
    private boolean status;

    @Column(name = "description", length = 50)
    private String description;

    @OneToMany(mappedBy = "saloon", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Attachment> attachments;


    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setSaloon(this);
    }
}
