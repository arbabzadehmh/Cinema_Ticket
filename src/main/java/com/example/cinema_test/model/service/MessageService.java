package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.MessageNotFoundException;
import com.example.cinema_test.controller.exception.SupportNotFoundException;
import com.example.cinema_test.model.entity.Customer;
import com.example.cinema_test.model.entity.Message;
import com.example.cinema_test.model.entity.Support;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class MessageService implements Serializable {
    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Message save(Message message) throws Exception {
        entityManager.persist(message);
        return message;
    }

    @Transactional
    public Message edit(Message message) throws Exception {
        message = entityManager.find(Message.class, message.getId());
        if (message != null) {
            entityManager.merge(message);
            return message;
        }
        throw new MessageNotFoundException();
    }

    @Transactional
    public Message remove(Long id) throws Exception {
        Message message = entityManager.find(Message.class, id);
        if (message != null) {
            message.setDeleted(true);
            entityManager.merge(message);
            return message;
        }
        throw new MessageNotFoundException();
    }

    @Transactional
    public List<Message> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from messageEntity oo where oo.deleted=false order by id", Message.class)
                .getResultList();
    }

    @Transactional
    public Message findById(Long id) throws Exception {
        return entityManager.find(Message.class, id);
    }

    @Transactional
    public List<Message> findBySupport(Support support) throws Exception {
        return entityManager
                .createQuery("select oo from messageEntity oo where oo.deleted=false and oo.support.id=support.id", Message.class)
                .getResultList();
    }

    @Transactional
    public List<Message> findBySender(String sender) throws Exception {
        return entityManager
                .createQuery("select oo from messageEntity oo where oo.deleted=false and oo.sender=sender", Message.class)
                .getResultList();
    }


}
