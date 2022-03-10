package hellojpa;

import javax.persistence.*;

@Entity
public class Locker {

    @Id @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    // 양방향
    // 연관관계 주인이 아니라고 설정 mappedBy
    @OneToOne(mappedBy = "locker")
    private Member member;
}
