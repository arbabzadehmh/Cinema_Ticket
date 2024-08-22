package com.example.cinema_test.model.entity;

import com.example.cinema_test.model.entity.enums.Genre;
import com.example.cinema_test.model.entity.enums.ShowType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity(name="showEntity")
@Table(name="show_tbl")
public class Show extends Base {

    @Id
    @SequenceGenerator(name = "showSeq", sequenceName = "show_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "showSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length =30 , nullable = false )
    private String name;

    @Column(name = "genre")
    @Enumerated(EnumType.ORDINAL)
    private Genre genre;

    @Column(name = "director", length =30)
    private String director;

    @Column(name = "producer", length =30)
    private String producer;

    @Column(name = "singer", length =30)
    private String singer;

    @Column(name = "speaker", length =30)
    private String speaker;

    @Column(name = "released_date")
    private LocalDate releasedDate;

    @Column(name = "base_price")
    private double basePrice;

    @Column(name = "show_type")
    @Enumerated(EnumType.ORDINAL)
    private ShowType showType;

    @Column(name = "available")
    private boolean available = false;

    @Column(name = "status")
    private boolean status;

    @Column(name = "description", length =50)
    private String description;

    @OneToMany(mappedBy = "show", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Attachment> attachments;


    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setShow(this);
    }

}
