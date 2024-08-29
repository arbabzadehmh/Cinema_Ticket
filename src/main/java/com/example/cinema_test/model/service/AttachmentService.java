package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.AttachmentNotFoundException;
import com.example.cinema_test.model.entity.Attachment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;


@ApplicationScoped
public class AttachmentService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;


    @Transactional
    public Attachment remove(Long id) throws Exception {
        Attachment attachment = entityManager.find(Attachment.class, id);
        if (attachment != null) {
            entityManager.remove(attachment);
            entityManager.flush();  // Force deletion to the database
            return attachment;
        }
        throw new AttachmentNotFoundException();
    }


}
