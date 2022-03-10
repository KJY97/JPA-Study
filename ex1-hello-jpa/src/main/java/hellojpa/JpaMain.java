package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

            Member member = new Member();
            member.setUsername("member1");

            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            // 이 부분이 애매하다. team 테이블이 insert 되는 부분이 아니다.
            // 이 외래키는 member 테이블을 가리키고 있어서 member 테이블이 바뀐다.(update 실행)
            // 왜냐하면 team 엔티티(teamA)가 TEAM 테이블에 저장이 되면서, 같이 연결된 member 테이블의 TEAM_ID는 FK이므로 update를 하는 수밖에 없다.
            team.getMembers().add(member);

            em.persist(team);

            tx.commit(); // 트랜잭션 종료
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}