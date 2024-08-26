package com.example.cinema_test.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


@Entity(name="userEntity")
@Table(name="user_tbl")
public class User extends Base{

    @Id
    @Column(name = "username", length = 30)
    private String username;

    @Column(name = "password", length = 15, nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_name"),
            foreignKey = @ForeignKey(name = "fk_user_role"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_user_role")
    )
    private Role role;

    @Column(name = "locked")
    private boolean locked;

}
