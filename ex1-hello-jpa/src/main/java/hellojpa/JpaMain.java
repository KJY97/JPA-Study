package hellojpa;

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
            System.out.println("m3 = " + reference.getClass()); // Proxy

            // 만약 영속성 컨텍스트를 종료하거나, 영속성 관리는 더 이상안하겠다고 선언하면 문제 발생 => LazyInitializationException
//            em.close();
//            em.detach(reference);

            em.clear(); // 추가로 clear로해도 문제 발생

            // em.close() or em.detach()를 안하면 문제 발생 안함
            // 실제 DB로 쿼리가 날아가면서 초기화가 된다.
            reference.getUsername();

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