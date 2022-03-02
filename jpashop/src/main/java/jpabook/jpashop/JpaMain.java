package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        // entityManagerFacory의 약자 emf
        // hello는 persistence.xml에서 작성 persistence-unit의 name이다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // entityManager의 약자 em
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 데이터베이스 트랜잭션 시작

        // 문제가 발생할 때를 대비하는 정석 코드 try catch문
        try {

            // order를 주문한 회원id 찾기
            Order order = em.find(Order.class, 1L);
            Long memberId = order.getMemberId(); // 식별자가 있으면 끊겨서 들어가는 느낌 => 이런 것을 객체 설계가 아닌 관계형DB 설계

            Member member = em.find(Member.class, memberId);
            // 이렇게만 보면 객체지향스럽지 않다!

            Member findMember = order.getMember();
            // 객체지향스럽다!

            tx.commit(); // 트랜잭션 종료
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}