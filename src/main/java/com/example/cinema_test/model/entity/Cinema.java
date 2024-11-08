package com.example.cinema_test.model.entity;


import jakarta.json.bind.annotation.JsonbTransient;
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


@Entity(name = "cinemaEntity")
@Table(name = "cinema_tbl")
public class Cinema extends Base{

    @Id
    @SequenceGenerator(name = "cinemaSeq", sequenceName = "cinema_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cinemaSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length =30 , nullable = false, unique = true )
    @Pattern(regexp = "^[a-zA-z\\d\\s]{2,30}$", message = "invalid Name")
    private String name;

    @Column(name = "address", length = 100)
    @Pattern(regexp = "^[\\w\\s]{2,100}$", message = "invalid Address")
    private String address;

    @Column(name = "status")
    private boolean status;

    @Column(name = "description", length = 50)
    private String description;

    @JsonbTransient
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cinema_saloon_tbl",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "saloon_id"),
            foreignKey = @ForeignKey(name = "fk_cinema_saloon"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_cinema_saloon")
    )
    private List<Saloon> saloonList;

    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cinema_show_tbl",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id"),
            foreignKey = @ForeignKey(name = "fk_cinema_show"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_cinema_show")
    )
    private List<Show> showList;

    @JsonbTransient
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cinema_show_time_tbl",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "show_time_id"),
            foreignKey = @ForeignKey(name = "fk_cinema_show_time"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_cinema_show_time")
    )
    private List<ShowTime> showTimeList;

    @JsonbTransient
    @OneToMany(mappedBy = "cinema", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;


    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setCinema(this);
    }

    public void addSaloon(Saloon saloon) {
        if (saloonList == null) {
            saloonList = new ArrayList<>();
        }
        saloonList.add(saloon);
    }

    public void addShow(Show show) {
        if (showList == null) {
            showList = new ArrayList<>();
        }
        showList.add(show);
    }

    public void addShowTime(ShowTime showTime){
        if (showTimeList == null){
            showTimeList = new ArrayList<>();
        }
        showTimeList.add(showTime);
    }

}
