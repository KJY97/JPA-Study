package hellojpa;

import javax.persistence.*;

@Entity // 반드시 넣어줘야 한다! 그래야 처음 jpa가 로딩될 때 인식 가능함
//@Table(name = "USER") // 만약 DB의 memeber이름이 User라면 이렇게 지정해준다.
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 일대다 양방향 매핑을 만들기 위한, 다대일 단방향 매핑
//    @ManyToOne(fetch = FetchType.LAZY) // Proxy 객체 조회를 한다. (지연 로딩)
    @ManyToOne(fetch = FetchType.EAGER) // 즉시 로딩
    @JoinColumn
    private Team team;

    // 모든 테이블에 반드시 있어야 하는 속성 -> 일일이 모든 테이블에 복사하기에는 중복되는 느낌이 있다.
    // 이럴 때 사용하는 것이 BaseEntity
//    private String createdBy;
//    private LocalDateTime createDate;
//    private String lastModifiedBy;
//    private LocalDateTime lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
