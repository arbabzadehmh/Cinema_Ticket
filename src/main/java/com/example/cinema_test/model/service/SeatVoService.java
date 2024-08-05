package com.example.cinema_test.model.service;


import com.example.cinema_test.controller.exception.SeatVoNotFoundException;
import com.example.cinema_test.model.entity.SeatVo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class SeatVoService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public SeatVo save(SeatVo seatVo) throws Exception {
        entityManager.persist(seatVo);
        return seatVo;
    }

    @Transactional
    public SeatVo edit(SeatVo seatVo) throws Exception {
        SeatVo foundSeatVo = entityManager.find(SeatVo.class, seatVo.getId());
        if (foundSeatVo != null) {
            entityManager.merge(seatVo);
            return seatVo;
        }
        throw new SeatVoNotFoundException();
    }

    @Transactional
    public SeatVo remove(Long id) throws Exception {
        SeatVo seatVo = entityManager.find(SeatVo.class, id);
        if (seatVo != null) {
            seatVo.setDeleted(true);
            entityManager.merge(seatVo);
            return seatVo;
        }
        throw new SeatVoNotFoundException();
    }

    @Transactional
    public List<SeatVo> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from seatVoEntity oo where oo.deleted=false order by id", SeatVo.class)
                .getResultList();
    }

    @Transactional
    public SeatVo findById(Long id) throws Exception {
        return entityManager.find(SeatVo.class, id);
    }

    @Transactional
    public List<SeatVo> findByStatus(boolean status) throws Exception {
        return entityManager
                .createQuery("select s from seatVoEntity s where s.status=:status and s.deleted=false", SeatVo.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Transactional
    public SeatVo findBySeatRowAndNumber(int rowNumber, int seatNumber) throws Exception {
        List<SeatVo> seatVoList =
                entityManager
                        .createQuery("select s from seatVoEntity s where s.rowNumber =:rowNumber and s.seatNumber =:seatNumber and s.deleted=false", SeatVo.class)
                        .setParameter("rowNumber", rowNumber)
                        .setParameter("seatNumber", seatNumber)
                        .getResultList();
        if (!seatVoList.isEmpty()) {
            return seatVoList.get(0);
        } else {
            return null;
        }
    }
}
