package com.example.cinema_test.model.entity;

import jakarta.json.bind.annotation.JsonbTransient;
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

@MappedSuperclass
public class Base implements Serializable {


    @JsonbTransient
    @Column(name = "deleted")
    private boolean deleted = false;

    @JsonbTransient
    @Column(name = "editing")
    private boolean editing = false;


}
