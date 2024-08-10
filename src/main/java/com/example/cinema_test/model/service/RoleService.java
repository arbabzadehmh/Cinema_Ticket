package com.example.cinema_test.model.service;


import com.example.cinema_test.controller.exception.RoleNotFoundException;
import com.example.cinema_test.model.entity.Admin;
import com.example.cinema_test.model.entity.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class RoleService implements Serializable {
    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Role save(Role role) throws Exception {
        entityManager.persist(role);
        return role;
    }

    @Transactional
    public Role edit(Role role) throws Exception {
        role = entityManager.find(Role.class, role.getRole());
        if (role != null) {
            entityManager.merge(role);
            return role;
        }
        throw new RoleNotFoundException();
    }

    @Transactional
    public Role remove(Long id) throws Exception {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            role.setDeleted(true);
            entityManager.merge(role);
            return role;
        }
        throw new RoleNotFoundException();
    }

    @Transactional
    public List<Role> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from roleEntity  oo where oo.deleted=false order by id", Role.class)
                .getResultList();
    }

    @Transactional
    public Role FindByRole(String name) throws Exception {
        List<Role> roleList =
                entityManager
                        .createQuery("select oo from roleEntity oo where oo.role =:name and oo.deleted=false ", Role.class)
                        .setParameter("name", name)
                        .getResultList();
        if (!roleList.isEmpty()) {
            return roleList.get(0);
        } else {
            return null;
        }
    }
}


