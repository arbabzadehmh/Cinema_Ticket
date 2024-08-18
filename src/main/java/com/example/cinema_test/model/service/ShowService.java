package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.ShowNotFoundException;
import com.example.cinema_test.model.entity.Show;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ShowService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Show save(Show show) throws Exception {
        entityManager.persist(show);
        return show;
    }

    @Transactional
    public Show edit(Show show) throws Exception {
        Show foundShow = entityManager.find(Show.class, show.getId());
        if (foundShow != null) {
            entityManager.merge(show);
            return show;
        }
        throw new ShowNotFoundException();
    }

    @Transactional
    public Show remove(Long id) throws Exception {
        Show show = entityManager.find(Show.class, id);
        if (show != null) {
            show.setDeleted(true);
            entityManager.merge(show);
            return show;
        }
        throw new ShowNotFoundException();
    }

    @Transactional
    public List<Show> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from showEntity oo where oo.deleted=false order by id", Show.class)
                .getResultList();
    }

    @Transactional
    public Show findById(Long id) throws Exception {
        return entityManager.find(Show.class, id);
    }

    @Transactional
    public List<Show> findActiveShows() throws Exception {
        return entityManager
                .createQuery("select s from showEntity s where s.available=true and s.deleted=false ", Show.class)
                .getResultList();
    }
    
}
