package com.example.cinema_test.model.entity;


import com.example.cinema_test.model.entity.enums.FileType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@Entity(name="attachmentEntity")
@Table(name="attachment_tbl")
public class Attachment {

    @Id
    @SequenceGenerator(name = "attachmentSeq", sequenceName = "attachment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachmentSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name", length = 50)
    private String fileName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "file_type")
    private FileType fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "attach_time")
    private LocalDateTime attachTime;

    @Column(name = "description", length = 50)
    private String description;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_cinema"))
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_show"))
    private Show show;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_manager"))
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_moderator"))
    private Moderator moderator;

    @ManyToOne
    @JoinColumn(name = "saloon_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_saloon"))
    private Saloon saloon;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_ticket"))
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_customer"))
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_admin"))
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_message"))
    private Message message;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_payment"))
    private Payment payment;
}
