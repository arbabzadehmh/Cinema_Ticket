package com.example.cinema_test.model.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@MappedSuperclass
public class Base implements Serializable {

    @JsonbTransient
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "attach_id")
    private List<Attachment> attachmentList;

    @JsonbTransient
    @Column(name = "deleted")
    private boolean deleted = false;


    public void addAttachment(Attachment attachment){
        if (this.attachmentList == null){
            this.attachmentList = new ArrayList<>();
        }
        attachmentList.add(attachment);
    }

}
