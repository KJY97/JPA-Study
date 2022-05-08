package hellojpa;

import javax.persistence.*;
import java.util.List;

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

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1); // 연관관계 메소드
            parent.addChild(child2);

            // 만약 casecade를 사용하지 않는다면 em.persist를 아래와 같이 호출을 해야만 insert문이 3개가 된다
            // 왜냐하면 엔티티를 저장할 때는 연관된 모든 엔티티는 영속 상태여야 하기 때문!
//            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);

            // CASECADE를 Parent에 추가하고 나면 em.persist를 한번만 호출하면 된다.
            em.persist(parent);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace(); // 에러를 확인하기 위해 추가해야 한다!
        } finally {
            em.close();
        }

        emf.close();
    }

}