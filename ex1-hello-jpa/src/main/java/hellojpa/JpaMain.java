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

            // 상속관계 매핑 확인해보기
            Movie movie = new Movie();
            movie.setDirector("aaa");
            movie.setActor("bbb");
            movie.setName("바람과함께사라지다");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            // 조회할 때에는 Join을 통해 조회한다. inner join 사용
            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

            // 구현 클래스마다 테이블 전략으로 할 시 item과 같은 것을 찾으려고 하면 모든 테이블을 찾아봐야 한다. (비효율적)
//            Item item = em.find(Item.class, movie.getId());
//            System.out.println("item = " + item);

            // MappedSuperclass 확인
            Member member = new Member();
            member.setUsername("user1");
            member.setCreatedBy("kim");
            member.setCreateDate(LocalDateTime.now());

            em.persist(member);

            tx.commit(); // 트랜잭션 종료
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}