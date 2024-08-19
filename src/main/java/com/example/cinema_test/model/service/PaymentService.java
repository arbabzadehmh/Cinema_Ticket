package com.example.cinema_test.model.service;

import com.example.cinema_test.model.entity.Payment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PaymentService implements Serializable {

    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Payment save(Payment payment) throws Exception {
        entityManager.persist(payment);
        return payment;
    }

    @Transactional
    public Payment edit(Payment payment) throws Exception {
        Payment findPayment = entityManager.find(Payment.class, payment.getId());
        if (findPayment != null) {
            entityManager.merge(payment);
            return payment;
        }
        throw new Exception();
    }
    @Transactional
    public Payment remove(Long id) throws Exception {
        Payment payment = entityManager.find(Payment.class, id);
        if (payment != null) {
            payment.setDeleted(true);
            entityManager.merge(payment);
            return payment;
        }
        throw new Exception();
    }
    @Transactional
    public List<Payment> findAll() throws Exception {
        return entityManager
                .createQuery("select p from paymentEntity p where p.deleted=false order by id", Payment.class)
                .getResultList();
    }
    @Transactional
    public Payment findById(Long id) throws Exception {
        return entityManager.find(Payment.class, id);
    }
    @Transactional
    public Payment findByDateTime(LocalDateTime localDateTime) throws Exception {
        List<Payment> paymentList = entityManager
                .createQuery("select p from paymentEntity p where p.paymentDateTime=:paymentDateTime and p.deleted=false ", Payment.class)
                .getResultList();
        if (!paymentList.isEmpty()) {
            return paymentList.get(0);
        }
        throw new Exception();
    }
}
