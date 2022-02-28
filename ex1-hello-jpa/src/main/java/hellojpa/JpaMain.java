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
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(5) // 5번부터
//                    .setMaxResults(8) // 8개 가져오기
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.Name = " + member.getName());
//            }

            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");
//
//            System.out.println("=== BEFORE ===");
//            // 영속
//            // 바로 DB에 저장하는 것이 아닌 1차 캐시에 저장한다.
//            em.persist(member);
//            System.out.println("=== AFTER ===");

            // 1차 캐시로 접근해서 101L을 바로 조회할 수 있다. 그래서 select 쿼리문이 안나옴
//            Member findMember = em.find(Member.class, 101L);
//            System.out.println("findMember.Id = " + findMember.getId());
//            System.out.println("findMember.Name = " + findMember.getName());

            // find1을 조회할 때는 select 쿼리문 출력, find2에서는 안나온다!
//            Member findMember1 = em.find(Member.class, 100L);
//            Member findMember2 = em.find(Member.class, 100L);
//
//            // find1과 find2 비교하기 => true
//            System.out.println("result = " + (findMember1 == findMember2));

            // 트랜잭션 쓰기 지연
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            System.out.println("===============");

            // 엔티티 수정
//            Member member3 = em.find(Member.class, 150L);
//            member3.setName("zzzz");

            // 플러시
//            Member member4 = new Member(200L, "member200");
//            em.persist(member4);
//            em.flush();

            // 준영속
            // 아래는 영속상태
//            Member member5 = em.find(Member.class, 150L);
//            member5.setName("AAAA");

            // 영속->준영속
            // commit할 시 아무런 일도 반응하지 않는다.
//            em.detach(member5);

            // 영속성 컨텍스트(1차 캐시)를 완전히 초기화
//           em.clear();
//
//            Member member6 = em.find(Member.class, 150L);
//
//            System.out.println("===============");

            /** 연관관계 매핑 기초 **/
            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            //역방향(주인이 아닌 방향)만 연관관계 설정
//            team.getMembers().add(member); // JPA가 알아서 team이 외래 키임을 알아서 연결한다. => 테이블에는 null 값이 들어감
            em.persist(team);

            // 회원 저장
            Member member = new Member();
            member.setUsername("member1");
//            member.setTeamId(team.getId());
            member.setTeam(team); // JPA가 알아서 team이 외래 키임을 알아서 연결한다. 얘가 포인트!! 조심하자
            em.persist(member);

            // db에서 가져온 쿼리를 보고 싶다면 영속성 컨텍스트 초기화
            em.flush();
            em.clear();

            //조회
//            Member findMember = em.find(Member.class, member.getId());
            //연관관계가 없음
//            Team findTeam = em.find(Team.class, team.getId());
            //참조를 사용해서 연관관계 조회
//            Team findTeam = findMember.getTeam();
//            System.out.println("findTeam = " + findTeam.getName());
//
//            // db 외래키값 수정 가능
//            Team newTeam = em.find(Team.class, 100L); // 100번에 찾는 team 정보가 있다고 가정한다
//            findMember.setTeam(newTeam); // 찾은 멤버를 newTeam으로 변경


            // 멤버에서 팀으로, 팀에서 다시 멤버로 이것이 바로 양방향향
//           List<Member> members = findMember.getTeam().getMembers();
//           for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//            }

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
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