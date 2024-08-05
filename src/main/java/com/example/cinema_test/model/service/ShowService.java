package com.example.cinema_test.model.service;

import com.example.cinema_test.model.entity.Show;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;

@ApplicationScoped
public class ShowService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Show save(Show show) throws Exception {
        entityManager.persist(show);
        return show;
    }
}
