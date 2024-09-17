package com.example.cinema_test.model.service;

import com.example.cinema_test.controller.exception.TicketNotFoundException;
import com.example.cinema_test.model.entity.Ticket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
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
        List<Ticket> ticketList =
                entityManager
                        .createQuery("select t from ticketEntity t where t.id=:id and t.deleted=false", Ticket.class)
                        .setParameter("id", id)
                        .getResultList();
        if (!ticketList.isEmpty()) {
            return ticketList.get(0);
        } else {
            return null;
        }
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
    public List<Ticket> findByCustomerPhoneNumber(String phoneNumber) throws Exception {
        return entityManager
                .createQuery("select t from ticketEntity t where t.customer.phoneNumber =:phoneNumber and t.deleted=false order by t.issueTime desc ", Ticket.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }

    @Transactional
    public List<Long> findSoldSeatsByShowId(Long showId) throws Exception {
        return entityManager
                .createQuery("select t.seatId from ticketEntity t where t.showTime.id =:showId and t.reserved=false and t.payment is not null and t.deleted=false ", Long.class)
                .setParameter("showId", showId)
                .getResultList();
    }

    @Transactional
    public List<Long> findReservedSeatsByShowId(Long showId) throws Exception {
        return entityManager
                .createQuery("select t.seatId from ticketEntity t where t.showTime.id =:showId and t.reserved=true and t.payment is null and t.deleted=false ", Long.class)
                .setParameter("showId", showId)
                .getResultList();
    }

    @Transactional
    public List<Ticket> findFailedTickets() throws Exception {
        return entityManager
                .createQuery("select t from ticketEntity t where t.reserved=true and t.issueTime <: allowedTime and t.deleted=false ", Ticket.class)
                .setParameter("allowedTime", LocalDateTime.now().minusMinutes(15))
                .getResultList();
    }

    @Transactional
    public List<Ticket> findByShowTimeId(Long showTimeId) throws Exception {
        return entityManager
                .createQuery("select t from ticketEntity t where t.showTime.id=:showTimeId and t.deleted=false order by t.issueTime desc ", Ticket.class)
                .setParameter("showTimeId", showTimeId)
                .getResultList();
    }


}
