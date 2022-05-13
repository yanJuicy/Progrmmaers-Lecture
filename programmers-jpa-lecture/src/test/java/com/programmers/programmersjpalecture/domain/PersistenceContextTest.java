package com.programmers.programmersjpalecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
@Slf4j
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void 저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        em.persist(customer);
        transaction.commit();

        Customer entity = em.find(Customer.class, 1L);
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

    @Test
    void 조회() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("guppy");
        customer.setLastName("kang");

        em.persist(customer);
        transaction.commit();

        em.clear();

        Customer entity = em.find(Customer.class, 2L);
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
        em.find(Customer.class, 2L);
    }

    @Test
    void 수정() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer entity = em.find(Customer.class, 1L);
        customer.setFirstName("guppy");
        customer.setLastName("hong");

        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer entity = em.find(Customer.class, 1L);
        em.remove(entity);

        transaction.commit();
    }

}
