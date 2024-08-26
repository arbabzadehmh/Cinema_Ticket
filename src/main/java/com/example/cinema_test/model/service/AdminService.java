package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.AdminNotFoundException;
import com.example.cinema_test.model.entity.Admin;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class AdminService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Admin save(Admin admin) throws Exception {
        entityManager.persist(admin);
        return admin;
    }

    @Transactional
    public Admin edit(Admin admin) throws Exception {
        Admin foundAdmin = entityManager.find(Admin.class, admin.getId());
        if (foundAdmin != null) {
            entityManager.merge(admin);
            return admin;
        }
        throw new AdminNotFoundException();
    }

    @Transactional
    public Admin remove(Long id) throws Exception {
        Admin admin = entityManager.find(Admin.class, id);
        if (admin != null) {
            admin.setDeleted(true);
            entityManager.merge(admin);
            return admin;
        }
        throw new AdminNotFoundException();
    }

    @Transactional
    public List<Admin> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from adminEntity oo where oo.deleted=false order by id", Admin.class)
                .getResultList();
    }

    @Transactional
    public Admin findById(Long id) throws Exception {
        return entityManager.find(Admin.class, id);
    }

    @Transactional
    public List<Admin> findByNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select a from adminEntity a where a.name like :name and a.family like :family and a.deleted=false ", Admin.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    @Transactional
    public Admin findByPhoneNumber(String phoneNumber) throws Exception {
        List<Admin> adminList =
                entityManager
                        .createQuery("select a from adminEntity a where a.phoneNumber =:phoneNumber and a.deleted=false ", Admin.class)
                        .setParameter("phoneNumber", phoneNumber)
                        .getResultList();
        if (!adminList.isEmpty()){
            return adminList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Admin findByUsername(String username) {
        List<Admin> adminList =
                entityManager
                        .createQuery("select a from adminEntity a where a.user.username =:username and a.deleted=false ", Admin.class)
                        .setParameter("username", username)
                        .getResultList();
        if (!adminList.isEmpty()){
            return adminList.get(0);
        } else {
            return null;
        }
    }
}
