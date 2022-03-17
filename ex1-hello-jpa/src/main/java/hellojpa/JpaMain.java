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
            Member member = new Member();
            member.setUsername("hello");

            em.persist(member);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
//            printMemberAndTeam(member); // 멤버와 회원 정보를 같이 출력
//            printMember(findmember); // 멤버만 출력
            // 위와 같이 멤버만 출력하는 경우와 회원정보까지 출력하려고 하는 경우 등 경우에 따라서 달라진다.
            // 무엇보다 printMember의 경우 회원 엔티티만 사용해서 팀 엔티티까지 조회하는 것은 효율적이지 않다.
            // 이러한 문제를 해결하는 것이 프록시

            // em.find()로 진행할 경우 sout(username)이 없더라도 select문이 나온다.
            // 그러나 em.getReference()는 sout(username)이 없다면 select문은 실행되지 않는다. sout과 같이 사용하는 시점에서 바로 조회
            // 가짜 엔티티 객체 조회
            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println("before findMember = " + findMember.getClass());
            System.out.println("findMember.id= " + findMember.getId()); // DB에 접근하지 않고 id를 가져올 수 있다.
            System.out.println("findMember.username = " + findMember.getUsername()); // DB에 접근해야만 username을 얻어올 수 있다.
            System.out.println("after findMember = " + findMember.getClass()); // before와 after의 프록시는 똑같다. 

            // ========================================

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);
            
            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);
            
            em.flush();
            em.clear();

            // m1과의 em.find()와 em.getReference() 타입 비교
            Member m1 = em.find(Member.class, member1.getId());

            // em.find()로 했을 시 타입 비교
//            Member m2 = em.find(Member.class, member2.getId());
//            System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // true

            // em.getReference()로 했을 시 타입 비교
            Member m3 = em.getReference(Member.class, member2.getId());
            System.out.println("m1 == m3 : " + (m1.getClass() == m3.getClass())); // false

            // 실제로 타입을 비교하고 체크할 때는 instanceof 를 사용한다.
            System.out.println("m1 instanceof Member " + (m1 instanceof Member));
            System.out.println("m3 instanceof Member " + (m3 instanceof Member));

            // 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.Reference()를 해도 실제 엔티티 반환
            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("m1.getClass() = " + m1.getClass()); // class hellojpa.Member
            System.out.println("reference.getClass() = " + reference.getClass()); // class hellojpa.Member

            System.out.println("m1 == reference : " + (m1 == reference)); //true

            tx.commit(); // 트랜잭션 종료
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
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