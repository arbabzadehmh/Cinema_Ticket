package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.ManagerNotFoundException;
import com.example.cinema_test.model.entity.Admin;
import com.example.cinema_test.model.entity.Manager;
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
                .setParameter("name", name)
                .setParameter("family", family)
                .getResultList();
    }

    @Transactional
    public Manager findByPhoneNumber(String phoneNumber) throws Exception {
        List<Manager> managerList =
                entityManager
                        .createQuery("select m from managerEntity m where m.phoneNumber =:phoneNumber and m.deleted=false ", Manager.class)
                        .setParameter("phoneNumber", phoneNumber)
                        .getResultList();
        if (!managerList.isEmpty()){
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
        if (!managerList.isEmpty()){
            return managerList.get(0);
        } else {
            return null;
        }
    }

}
