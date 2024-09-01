package com.example.cinema_test.model.service;


import com.example.cinema_test.controller.exception.AccessDeniedException;
import com.example.cinema_test.controller.exception.DuplicateUserException;
import com.example.cinema_test.controller.exception.UserNotFoundException;

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
        List<User> userList =entityManager
                .createQuery("select u from userEntity u where u.username =:username and u.deleted=false", User.class)
                        .setParameter("username", user.getUsername())
                                .getResultList();
        if (!userList.isEmpty()) {
            throw new DuplicateUserException();
        }
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public User edit(User user) throws Exception {
        User foundUser = entityManager.find(User.class, user.getUsername());
        if (foundUser != null) {
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

    public User findByUsername(String username) throws Exception {
        List<User> userList =
                entityManager
                        .createQuery("select oo from userEntity oo where oo.username=:username", User.class)
                        .setParameter("username", username)
                        .getResultList();
        if (!userList.isEmpty()) {
            return userList.get(0);
        } else {
            throw new UserNotFoundException();
        }
    }

    public User findByUsernameAndPassword(String username, String password) throws Exception {
        List<User> userList =
                entityManager
                        .createQuery("select oo from userEntity oo where oo.username=:username and oo.password=:password", User.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .getResultList();
        if (!userList.isEmpty()) {
            return userList.get(0);
        } else {
            throw new AccessDeniedException();
        }
    }


}
