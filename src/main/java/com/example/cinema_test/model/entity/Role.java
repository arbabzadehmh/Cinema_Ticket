package com.example.cinema_test.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity(name="roleEntity")
@Table(name="role_tbl")
public class Role extends Base {

    @Id
    @Column(name = "role_name", length = 20, unique = true)
    private String role;
}
