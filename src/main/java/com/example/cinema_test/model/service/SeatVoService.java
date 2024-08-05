package com.example.cinema_test.model.service;


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
    public List<SeatVo> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from seatVoEntity oo where oo.deleted=false order by id", SeatVo.class)
                .getResultList();
    }
}
