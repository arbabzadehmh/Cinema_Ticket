package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.TicketNotFoundException;
import com.example.cinema_test.model.entity.Ticket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class TicketService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Ticket save(Ticket ticket) throws Exception {
        entityManager.persist(ticket);
        return ticket;
    }

    @Transactional
    public Ticket edit(Ticket ticket) throws Exception {
        Ticket foundTicket = entityManager.find(Ticket.class, ticket.getId());
        if (foundTicket != null) {
            entityManager.merge(ticket);
            return ticket;
        }
        throw new TicketNotFoundException();
    }

    @Transactional
    public Ticket remove(Long id) throws Exception {
        Ticket ticket = entityManager.find(Ticket.class, id);
        if (ticket != null) {
            ticket.setDeleted(true);
            entityManager.merge(ticket);
            return ticket;
        }
        throw new TicketNotFoundException();
    }

    @Transactional
    public List<Ticket> findAll() throws Exception {
        return entityManager
                .createQuery("select oo from ticketEntity oo where oo.deleted=false order by id", Ticket.class)
                .getResultList();
    }

    @Transactional
    public Ticket findById(Long id) throws Exception {
        return entityManager.find(Ticket.class, id);
    }

    @Transactional
    public List<Ticket> findByCustomerNameAndFamily(String name, String family) throws Exception {
        return entityManager
                .createQuery("select t from ticketEntity t where t.customer.name like :name and t.customer.family like :family and t.deleted=false ", Ticket.class)
                .setParameter("name", name + "%")
                .setParameter("family", family + "%")
                .getResultList();
    }

    @Transactional
    public Ticket findByCustomerPhoneNumber(String phoneNumber) throws Exception {
        List<Ticket> ticketList =
                entityManager
                        .createQuery("select t from ticketEntity t where t.customer.phoneNumber =:phoneNumber and t.deleted=false ", Ticket.class)
                        .setParameter("phoneNumber", phoneNumber)
                        .getResultList();
        if (!ticketList.isEmpty()){
            return ticketList.get(0);
        } else {
            return null;
        }
    }

}
