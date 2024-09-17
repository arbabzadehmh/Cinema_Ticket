package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.ManagerNotFoundException;
import com.example.cinema_test.model.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ManagerService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Manager save(Manager manager) throws Exception {
        entityManager.persist(manager);
        return manager;
    }

    @Transactional
    public Manager edit(Manager manager) throws Exception {
        Manager foundManager = entityManager.find(Manager.class, manager.getId());
        if (foundManager != null) {
            entityManager.merge(manager);
            return manager;
        }
        throw new ManagerNotFoundException();
    }

    @Transactional
    public Manager remove(Long id) throws Exception {
        Manager manager = entityManager.find(Manager.class, id);
        if (manager != null) {
            manager.setDeleted(true);
            entityManager.merge(manager);
            return manager;
        }
        throw new ManagerNotFoundException();
    }

    @Transactional
    public List<Manager> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from managerEntity oo where oo.deleted=false order by id", Manager.class)
                .getResultList();
    }

    @Transactional
    public Manager findById(Long id) throws Exception {
        return entityManager.find(Manager.class, id);
    }

    @Transactional
    public List<Manager> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.name like :name and m.family like :family and m.deleted=false ", Manager.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    @Transactional
    public Manager findByPhoneNumber(String phoneNumber) throws Exception {
        List<Manager> managerList =
                entityManager
                        .createQuery("select m from managerEntity m where m.phoneNumber =:phoneNumber and m.deleted=false ", Manager.class)
                        .setParameter("phoneNumber", phoneNumber)
                        .getResultList();
        if (!managerList.isEmpty()) {
            return managerList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Manager findByNationalCode(String nationalCode) throws Exception {
        List<Manager> managerList =
                entityManager
                        .createQuery("select m from managerEntity m where m.nationalCode =:nationalCode and m.deleted=false ", Manager.class)
                        .setParameter("nationalCode", nationalCode)
                        .getResultList();
        if (!managerList.isEmpty()) {
            return managerList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Manager findByUsername(String username) throws Exception {
        List<Manager> managerList =
                entityManager
                        .createQuery("select m from managerEntity m where m.user.username =:username and m.deleted=false ", Manager.class)
                        .setParameter("username", username)
                        .getResultList();
        if (!managerList.isEmpty()) {
            return managerList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Cinema findCinemaByManagerId(Long managerId) throws Exception {
        List<Cinema> cinemaList =
                entityManager
                        .createQuery("select m.cinema from managerEntity m where m.id =:managerId and m.deleted=false ", Cinema.class)
                        .setParameter("managerId", managerId)
                        .getResultList();
        if (!cinemaList.isEmpty()) {
            return cinemaList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Manager findManagerByCinemaId(Long cinemaId) throws Exception {
        List<Manager> managerList =
                entityManager
                        .createQuery("select m from managerEntity m where m.cinema.id =:cinemaId and m.deleted=false ", Manager.class)
                        .setParameter("cinemaId", cinemaId)
                        .getResultList();
        if (!managerList.isEmpty()) {
            return managerList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Show> findShowsByManagerId(Long managerId) throws Exception {
        return entityManager
                .createQuery("select m.cinema.showList from managerEntity m where m.id =:managerId and m.deleted=false ", Show.class)
                .setParameter("managerId", managerId)
                .getResultList();
    }

    @Transactional
    public List<Manager> findManagersWantingCinema() throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.cinema =null and m.deleted=false ", Manager.class)
                .getResultList();
    }

    @Transactional
    public List<Manager> findByFamily(String family) throws Exception {
        return entityManager
                .createQuery("select m from managerEntity m where m.family like :family and m.deleted=false ", Manager.class)
                .setParameter("family", family.toUpperCase() + "%")
                .getResultList();
    }


}
