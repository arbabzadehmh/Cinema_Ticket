package com.example.cinema_test.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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

    @Column(name = "name", length =30 , nullable = false )
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "status")
    private boolean status;

    @Column(name = "description", length = 50)
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "cinema_saloon_tbl",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "saloon_id"),
            foreignKey = @ForeignKey(name = "fk_cinema_saloon"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_cinema_saloon")
    )
    private List<Saloon> saloonList;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "cinema_show_tbl",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id"),
            foreignKey = @ForeignKey(name = "fk_cinema_show"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_cinema_show")
    )
    private List<Show> showList;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "cinema_show_time_tbl",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "show_time_id"),
            foreignKey = @ForeignKey(name = "fk_cinema_show_time"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_cinema_show_time")
    )
    private List<ShowTime> showTimeList;
}
