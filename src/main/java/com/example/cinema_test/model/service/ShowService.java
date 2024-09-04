package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.DuplicateShowException;
import com.example.cinema_test.controller.exception.ShowIsPlayingException;
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
        List<Show> existingShows = entityManager
                .createQuery("select s from showEntity s where s.name = :name and s.deleted = false", Show.class)
                .setParameter("name", show.getName())
                .getResultList();

        if (!existingShows.isEmpty()) {
            throw new DuplicateShowException();
        }
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
            if (show.isAvailable()) {
                throw new ShowIsPlayingException();
            }
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
        List<Show> showList =
                entityManager
                        .createQuery("select s from showEntity s where s.id =:id and s.deleted=false", Show.class)
                        .setParameter("id", id)
                        .getResultList();
        if (!showList.isEmpty()) {
            return showList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Show> findAvailableShows() throws Exception {
        return entityManager
                .createQuery("select s from showEntity s where s.available=true and s.status=true and s.deleted=false ", Show.class)
                .getResultList();
    }

    @Transactional
    public Show findByName(String name) throws Exception {
        List<Show> showList =
                entityManager
                        .createQuery("select s from showEntity s where s.name =:name and s.deleted=false", Show.class)
                        .setParameter("name", name.toUpperCase())
                        .getResultList();
        if (!showList.isEmpty()) {
            return showList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Show> findUsableShows() throws Exception {
        return entityManager
                .createQuery("select s from showEntity s where s.status=true and s.deleted=false ", Show.class)
                .getResultList();
    }

}
