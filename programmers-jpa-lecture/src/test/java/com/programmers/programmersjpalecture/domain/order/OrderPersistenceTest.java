package com.programmers.programmersjpalecture.domain.order;

import com.programmers.programmersjpalecture.domain.Member;
import com.programmers.programmersjpalecture.domain.Order;
import com.programmers.programmersjpalecture.domain.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void 잘못된_설계() {
        Member member = new Member();
        member.setName("kanghonggu");
        member.setAddress("서울시 동작구");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setDescription("백엔드 개발자");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        Member memberEntity = entityManager.find(Member.class, 1L);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("부재시 전화");
        order.setMemberId(memberEntity.getId());

        entityManager.persist(order);
        transaction.commit();

        Order orderEntity = entityManager.find(Order.class, order.getUuid());

        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());

        log.info("nick: {}", orderMemberEntity.getNickName());

    }

    @Test
    void 연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setName("kang");
        member.setNickName("guupy");
        member.setAddress("서울");
        member.setAge(33);

        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("부재시 연락");
        order.setMember(member);

        em.persist(order);
        transaction.commit();

        em.clear();
        Order entity = em.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getNickName());
        log.info("{}", entity.getMember().getOrders().size());
    }

    @Test
    void 양방향관계_저장_편의메소드() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화");
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);

        Member member = new Member();
        member.setName("kanghonggu");
        member.setNickName("guppy.kang");
        member.setAge(33);
        member.setAddress("서울시 동작구");
        member.setDescription("KDT ");

        member.addOrder(order);

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 객체그래프탐색을_이용한_조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("kanghonggu");
        member.setNickName("guppy.kag");
        member.setAge(33);
        member.setAddress("서울시 동작구만 움직이면쏜다.");
        member.setDescription("KDT 화이팅");

        member.addOrder(order); // 연관관계 편의 메소드 사용

        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        // 회원 조회 -> 회원의 주문 까지 조회
        Member findMember = entityManager.find(Member.class, 1L);
        log.info("order-memo: {}", findMember.getOrders().get(0).getMemo());

        // 주문조회 -> 주문한 회원 조회
        Order findOrder = entityManager.find(Order.class, findMember.getOrders().get(0).getUuid());
        log.info("member-nickName: {}", findOrder.getMember().getNickName());
    }

}
