package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.SupportNotFoundException;
import com.example.cinema_test.controller.exception.UserNotFoundException;
import com.example.cinema_test.model.entity.Support;
import com.example.cinema_test.model.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UserService implements Serializable {
    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public User save(User user) throws Exception {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public User edit(User user) throws Exception {
        user = entityManager.find(User.class, user.getUsername());
        if (user != null) {
            entityManager.merge(user);
            return user;
        }
        throw new UserNotFoundException();
    }

    @Transactional
    public User remove(String username) throws Exception {
        User user = entityManager.find(User.class, username);
        if (user != null) {
            user.setDeleted(true);
            entityManager.merge(user);
            return user;
        }
        throw new UserNotFoundException();
    }

    @Transactional
    public List<User> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from userEntity oo where oo.deleted=false order by username", User.class)
                .getResultList();
    }

    public List<User> findByUsername(String username) throws Exception {
        return entityManager
                .createQuery("select oo from userEntity oo where oo.username=:username", User.class)
                .setParameter("username", username )
                .getResultList();
    }

    public List<User> findByUsernameAndPassword(String username, String password) throws Exception {
        return entityManager
                .createQuery("select oo from userEntity oo where oo.username=:username and oo.password=:password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
    }


}
