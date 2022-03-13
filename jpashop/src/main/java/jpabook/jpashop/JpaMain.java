package jpabook.jpashop;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

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
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);

            tx.commit(); // 트랜잭션 종료
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}