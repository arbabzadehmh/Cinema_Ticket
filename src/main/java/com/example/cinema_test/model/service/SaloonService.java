package com.example.cinema_test.model.service;


import com.example.cinema_test.controller.exception.SaloonNotFoundException;
import com.example.cinema_test.model.entity.Saloon;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class SaloonService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Saloon save(Saloon saloon) throws Exception {
        entityManager.persist(saloon);
        return saloon;
    }

    @Transactional
    public Saloon edit(Saloon saloon) throws Exception {
        Saloon foundSaloon = entityManager.find(Saloon.class, saloon.getId());
        if (foundSaloon != null) {
            entityManager.merge(saloon);
            return saloon;
        }
        throw new SaloonNotFoundException();
    }

    @Transactional
    public Saloon remove(Long id) throws Exception {
        Saloon saloon = entityManager.find(Saloon.class, id);
        if (saloon != null) {
            saloon.setDeleted(true);
            entityManager.merge(saloon);
            return saloon;
        }
        throw new SaloonNotFoundException();
    }

    @Transactional
    public List<Saloon> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from saloonEntity oo where oo.deleted=false order by id", Saloon.class)
                .getResultList();
    }

    @Transactional
    public Saloon findById(Long id) throws Exception {
        return entityManager.find(Saloon.class, id);
    }

    @Transactional
    public Saloon findByName(String name) throws Exception {
        List<Saloon> saloonList =
                entityManager
                        .createQuery("select s from saloonEntity s where s.name =:name and s.deleted=false ", Saloon.class)
                        .setParameter("name", name)
                        .getResultList();
        if (!saloonList.isEmpty()) {
            return saloonList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Saloon> findByStatus(boolean status) throws Exception {
        return entityManager
                .createQuery("select s from saloonEntity s where s.status=:status and s.deleted=false", Saloon.class)
                .setParameter("status", status)
                .getResultList();
    }

}
