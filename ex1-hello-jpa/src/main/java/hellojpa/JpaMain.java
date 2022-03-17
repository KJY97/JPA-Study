package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

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
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            // 프록시 객체는 식별자 값을 가지고 있으므로 식별자 값을 조회하는 getId()를 호출해도 초기화하지 않는다.
            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("m3 = " + reference.getClass()); // 프록시인지 진짜 엔티티인지 확인하려면 getClass로 확인

            // JPA에서는 이 방법만 지원한다.
            reference.getUsername(); // 강제 호출을 통한 강제 초기화

            // 하이버네이트에서 제공하는 강제 초기화 방법
            Hibernate.initialize(reference); // 강제 초기화

           // 위 초기화 코드가 있으면 true, 없으면 false 반환
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(reference)); // 프록시 초기화 여부 확인

            tx.commit(); // 트랜잭션 종료
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace(); // 에러를 확인하기 위해 추가해야 한다!
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("member.getUsername() = " + member.getUsername());
    }

    // 멤버와 회원정보를 같이 출력
    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
                
    }
}