package com.example.cinema_test.model.service;


import com.example.cinema_test.controller.exception.ShowTimeNotFoundException;
import com.example.cinema_test.model.entity.Seat;
import com.example.cinema_test.model.entity.SeatVo;
import com.example.cinema_test.model.entity.ShowTime;
import com.example.cinema_test.model.entity.enums.ShowType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ShowTimeService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public ShowTime save(ShowTime showTime) throws Exception {

        for (Seat seat: showTime.getSaloon().getSeats()) {
            SeatVo seatVo = new SeatVo(seat);
            if (showTime.getShow().getShowType().equals(ShowType.MOVIE)) {
                seatVo.setPriceRatio(1);
            }
            showTime.getShowSeats().add(seatVo);
        }

        entityManager.persist(showTime);
        return showTime;
    }

    @Transactional
    public ShowTime edit(ShowTime showTime) throws Exception {
        ShowTime foundShowTime = entityManager.find(ShowTime.class, showTime.getId());
        if (foundShowTime != null) {
            entityManager.merge(showTime);
            return showTime;
        }
        throw new ShowTimeNotFoundException();
    }

    @Transactional
    public ShowTime remove(Long id) throws Exception {
        ShowTime showTime = entityManager.find(ShowTime.class, id);
        if (showTime != null) {
            showTime.setDeleted(true);
            entityManager.merge(showTime);
            return showTime;
        }
        throw new ShowTimeNotFoundException();
    }

    @Transactional
    public List<ShowTime> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from showTimeEntity oo where oo.deleted=false order by id", ShowTime.class)
                .getResultList();
    }

    @Transactional
    public ShowTime findById(Long id) throws Exception {
        return entityManager.find(ShowTime.class, id);
    }

    @Transactional
    public List<ShowTime> findByCinemaName(String name) throws Exception {
        return entityManager
                .createQuery("select s from showTimeEntity s where s.cinema.name =:name and s.deleted=false", ShowTime.class)
                .setParameter("name", name)
                .getResultList();
    }

}
