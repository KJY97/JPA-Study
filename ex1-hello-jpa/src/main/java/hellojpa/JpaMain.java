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
            // Member 저장
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");

//            em.persist(member); // JPA member를 DB에 저장

            // Member 조회
//            Member findMember = em.find(Member.class, 1L); // PK가 1L인 회원 찾기
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            // Member 삭제
//            Member findMember = em.find(Member.class, 1L);
//            em.remove(findMember);

            // Member 수정
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");

            // 전체 회원 조회
            // JPA 입장에서는 Table을 대상으로 쿼리문을 작성하지 않는다. 멤버 객체를 대상으로 쿼리문을 작성
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5) // 5번부터
                    .setMaxResults(8) // 8개 가져오기
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.Name = " + member.getName());
            }

            tx.commit(); // 트랜잭션 종료
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
