package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.CinemaNotFoundException;
import com.example.cinema_test.model.entity.Cinema;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;


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
    

}
