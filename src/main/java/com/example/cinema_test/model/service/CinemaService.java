package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.CinemaNotFoundException;
import com.example.cinema_test.model.entity.Cinema;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


import java.io.Serializable;
import java.util.List;


@ApplicationScoped
public class CinemaService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Cinema save(Cinema cinema) throws Exception {
        entityManager.persist(cinema);
        return cinema;
    }

    @Transactional
    public Cinema edit(Cinema cinema) throws Exception {
        Cinema foundCinema = entityManager.find(Cinema.class, cinema.getId());
        if (foundCinema != null) {
            entityManager.merge(cinema);
            return cinema;
        }
        throw new CinemaNotFoundException();
    }

    @Transactional
    public Cinema remove(Long id) throws Exception {
        Cinema cinema = entityManager.find(Cinema.class, id);
        if (cinema != null) {
            cinema.setDeleted(true);
            entityManager.merge(cinema);
            return cinema;
        }
        throw new Exception();
    }

    @Transactional
    public List<Cinema> findAll() throws Exception {
        return entityManager
                .createQuery("select c from cinemaEntity c where c.deleted=false order by id", Cinema.class)
                .getResultList();
    }

    @Transactional
    public Cinema findByName(String name) throws Exception {
        List<Cinema> cinemaList = entityManager
                .createQuery("select c from cinemaEntity c where c.name like :name and deleted=false ", Cinema.class)
                .setParameter("name", name)
                .getResultList();
        if (!cinemaList.isEmpty()) {
            return cinemaList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Cinema findByAddress(String address) throws Exception {
        List<Cinema> cinemaList = entityManager
                .createQuery("select c from cinemaEntity c where c.address like :address and c.deleted=false ", Cinema.class)
                .setParameter("address", address)
                .getResultList();
        if (!cinemaList.isEmpty()) {
            return cinemaList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Cinema findById(Long id) throws Exception {
        return entityManager.find(Cinema.class, id);
    }


}
