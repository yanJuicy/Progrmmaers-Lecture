package com.programmers.programmersjpalecture.domain.order;

import com.programmers.programmersjpalecture.domain.multikey.Parent;
import com.programmers.programmersjpalecture.domain.multikey.Parent2;
import com.programmers.programmersjpalecture.domain.multikey.ParentId;
import com.programmers.programmersjpalecture.domain.multikey.ParentId2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
public class MappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void multi_key_test() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Parent parent = new Parent();
        parent.setId1("id1");
        parent.setId2("id2");
        em.persist(parent);

        transaction.commit();

        Parent entity = em.find(Parent.class, new ParentId("id1", "id2"));
        log.info("{}, {}", entity.getId1(), entity.getId2());
    }

    @Test
    void multi_key_test_embedded() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Parent2 parent = new Parent2();
        parent.setId(new ParentId2("id1", "id2"));
        em.persist(parent);

        transaction.commit();

        Parent2 entity = em.find(Parent2.class, new ParentId2("id1", "id2"));
        log.info("{}, {}", entity.getId().getId1(), entity.getId().getId2());

    }

}
