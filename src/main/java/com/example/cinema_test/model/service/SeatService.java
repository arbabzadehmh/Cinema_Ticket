package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.SeatNotFoundException;
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
    public Seat edit(Seat seat) throws Exception {
        Seat foundSeat = entityManager.find(Seat.class, seat.getId());
        if (foundSeat != null) {
            entityManager.merge(seat);
            return seat;
        }
        throw new SeatNotFoundException();
    }

    @Transactional
    public Seat remove(Long id) throws Exception {
        Seat seat = entityManager.find(Seat.class, id);
        if (seat != null) {
            seat.setDeleted(true);
            entityManager.merge(seat);
            return seat;
        }
        throw new SeatNotFoundException();
    }

    @Transactional
    public List<Seat> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from seatEntity oo where oo.deleted=false order by id", Seat.class)
                .getResultList();
    }

    @Transactional
    public Seat findById(Long id) throws Exception {
        return entityManager.find(Seat.class, id);
    }

    @Transactional
    public List<Seat> findByStatus(boolean status) throws Exception {
        return entityManager
                .createQuery("select s from seatEntity s where s.status=:status and s.deleted=false", Seat.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Transactional
    public Seat findBySeatRowAndNumber(int rowNumber, int seatNumber) throws Exception {
        List<Seat> seatList =
         entityManager
                .createQuery("select s from seatEntity s where s.rowNumber =:rowNumber and s.seatNumber =:seatNumber and s.deleted=false", Seat.class)
                .setParameter("rowNumber", rowNumber)
                .setParameter("seatNumber", seatNumber)
                .getResultList();
        if (!seatList.isEmpty()){
            return seatList.get(0);
        } else {
            return null;
        }
    }
}
