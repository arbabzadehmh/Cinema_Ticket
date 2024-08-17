package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.SupportNotFoundException;
import com.example.cinema_test.model.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class SupportService implements Serializable {
    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Support save(Support support) throws Exception {
        entityManager.persist(support);
        return support;
    }

    @Transactional
    public Support edit(Support support) throws Exception {
        support = entityManager.find(Support.class, support.getId());
        if (support != null) {
            entityManager.merge(support);
            return support;
        }
        throw new SupportNotFoundException();
    }

    @Transactional
    public Support remove(Long id) throws Exception {
        Support support = entityManager.find(Support.class, id);
        if (support != null) {
            support.setDeleted(true);
            entityManager.merge(support);
            return support;
        }
        throw new SupportNotFoundException();
    }

    @Transactional
    public List<Support> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from supportEntity oo where oo.deleted=false order by id", Support.class)
                .getResultList();
    }

    @Transactional
    public Support findById(Long id) throws Exception {
        return entityManager.find(Support.class, id);
    }

    @Transactional
    public List<Support> findByModerator(Moderator moderator) throws Exception {
        return entityManager
                .createQuery("select oo from supportEntity oo where oo.deleted=false and oo.moderator.id=moderator.id", Support.class)
                .getResultList();
    }

    @Transactional
    public List<Support> findByCustomer(Customer customer) throws Exception {
        return entityManager
                .createQuery("select oo from supportEntity oo where oo.deleted=false and oo.customer.id=customer.id", Support.class)
                .getResultList();
    }

    @Transactional
    public List<Support> findByMessage(Message message) throws Exception {
        return entityManager
                .createQuery("select oo from supportEntity oo where oo.deleted=false and oo.message.id=message.id", Support.class)
                .getResultList();
    }


    @Transactional
    public List<Support> findByNotSolved() throws Exception {
        return entityManager
                .createQuery("select oo from supportEntity oo where oo.deleted=false and oo.solved=false ", Support.class)
                .getResultList();
    }

    @Transactional
    public List<Support> findBySolved() throws Exception {
        return entityManager
                .createQuery("select oo from supportEntity oo where oo.deleted=false and oo.solved=true ", Support.class)
                .getResultList();
    }


}
