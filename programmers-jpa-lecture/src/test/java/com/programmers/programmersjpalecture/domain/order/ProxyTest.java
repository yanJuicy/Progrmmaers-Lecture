package com.programmers.programmersjpalecture.domain.order;

import com.programmers.programmersjpalecture.domain.Member;
import com.programmers.programmersjpalecture.domain.Order;
import com.programmers.programmersjpalecture.domain.OrderItem;
import com.programmers.programmersjpalecture.domain.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.loader.plan.spi.QuerySpaceUidNotRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class ProxyTest {

    @Autowired
    EntityManagerFactory emf;

    UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setUuid(uuid.toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("kanghonggu");
        member.setNickName("guppy.kanggg");
        member.setAge(33);
        member.setAddress("서울시 동작구만 움직이면쏜다.");
        member.setDescription("KDT 화이팅");

        member.addOrder(order); // 연관관계 편의 메소드 사용
        entityManager.persist(member);
        transaction.commit();
    }

    @Test
    void proxy() {
        EntityManager entityManager = emf.createEntityManager();

        // 회원 조회 -> 회원의 주문 까지 조회
        Member findMember = entityManager.find(Member.class, 1L);

        log.info("orders is loaded : {}", entityManager.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(findMember.getOrders()));

        log.info("-------");
        log.info("{}" ,findMember.getOrders().get(0).getMemo());
        log.info("orders is loaded : {}", entityManager.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(findMember.getOrders()));
    }

    @Test
    void move_persist() {
        EntityManager em  = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Order order = em.find(Order.class, uuid.toString());

        transaction.begin();

        OrderItem item = new OrderItem();
        item.setQuantity(10);
        item.setPrice(1000);

//        order.addOrderItem(item);

        em.persist(item);

        transaction.commit();

        transaction.begin();

        em.remove(order);

        transaction.commit();

    }

    @Test
    void orphan() {
        EntityManager em = emf.createEntityManager();

        Member findMember = em.find(Member.class, 1L);
        findMember.getOrders().remove(0);

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        transaction.commit();

    }

}
