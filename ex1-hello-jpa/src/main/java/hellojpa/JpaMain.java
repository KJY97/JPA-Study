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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team teamB = new Team();
            team.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member1.getId());

            // 지연 로딩 시 Proxy로 출력
            // 즉시 로딩 시 진짜 엔티티 team 출력
           System.out.println("m = " + m.getTeam().getClass());

            System.out.println("=============");
            // 지연 로딩 시 m은 프록시이므로 이 시점에서 쿼리 등장 (초기화)
            // 즉시 로딩 시 teamName값 출력
            System.out.println("team name = " + m.getTeam().getName());
            System.out.println("=============");

            em.clear();
            em.flush();

            // JPQL에서 "select m from Member m"는 말 그대로 SQL로 해석된다.
            // 즉, SQL 대로 해석하면 Member 테이블을 그대로 가져와야 하는데 Team이 걸려서 Team도 조회
            // 만약 즉시 로딩이라면 team 객체가 2개일 때 team 2개 모두 나온다.. => N+1
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

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