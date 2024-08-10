package com.example.cinema_test.model.service;

import com.example.cinema_test.model.entity.Bank;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

public class BankService implements Serializable {
    @PersistenceContext(unitName = "cinema")
    private EntityManager entityManager;

    @Transactional
    public Bank save(Bank bank) throws Exception {
        entityManager.persist(bank);
        return bank;
    }

    @Transactional
    public Bank edit(Bank bank) throws Exception {
        Bank foundBank = entityManager.find(Bank.class, bank.getId());
        if (foundBank != null) {
            entityManager.merge(bank);
            return bank;
        }
        throw new Exception();
    }

    @Transactional
    public Bank remove(Long id) throws Exception {
        Bank bank = entityManager.find(Bank.class, id);
        if (bank != null) {
            bank.setDeleted(true);
            entityManager.merge(bank);
            return bank;
        }
        throw new Exception();
    }

    @Transactional
    public List<Bank> findAll() throws Exception {
        return entityManager.createQuery("select b from bankEntity b where b.status=:status and b.deleted=false order by id", Bank.class)
                .getResultList();
    }

    @Transactional
    public Bank findById(Long id) throws Exception {
        return entityManager.find(Bank.class, id);
    }

    @Transactional
    public Bank findByName(String name) throws Exception {
        List<Bank> bankList = entityManager.createQuery("select b from bankEntity b where b.name=:name and b.deleted=false", Bank.class)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        }
        throw new Exception();
    }

    @Transactional
    public Bank findAccountNumber(String accountNumber) throws Exception {
        List<Bank> bankList = entityManager.createQuery("select b from bankEntity b where b.accountNumber=:accountNumber and b.deleted=false", Bank.class)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        }
        throw new Exception();
    }

    @Transactional
    public Bank findByBranchName(String branchName) throws Exception {
        List<Bank> bankList = entityManager.createQuery("select b from bankEntity b where b.branchName=:branchName and b.deleted=false", Bank.class)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        }
        throw new Exception();
    }

    @Transactional
    public Bank findByBranchCode(Long branchCode) throws Exception {
        List<Bank> bankList = entityManager.createQuery("select b from bankEntity b where b.branchCode=:branchCode and b.deleted=false", Bank.class)
                .getResultList();
        if (!bankList.isEmpty()) {
            return bankList.get(0);
        }
        throw new Exception();
    }
}
