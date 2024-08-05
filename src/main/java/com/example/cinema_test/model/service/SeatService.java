package com.example.cinema_test.model.service;

import com.example.cinema_test.model.entity.Seat;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class SeatService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Seat save(Seat seat) throws Exception {
        entityManager.persist(seat);
        return seat;
    }

    @Transactional
    public List<Seat> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from seatEntity oo where oo.deleted=false order by id", Seat.class)
                .getResultList();
    }
}
