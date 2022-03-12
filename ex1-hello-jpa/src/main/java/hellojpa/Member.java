package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // 반드시 넣어줘야 한다! 그래야 처음 jpa가 로딩될 때 인식 가능함
//@Table(name = "USER") // 만약 DB의 memeber이름이 User라면 이렇게 지정해준다.
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 객체 관계 매핑! (다대일)
//    @ManyToOne // Member 입장에서 Many, Team 입장에서는 one
//    @JoinColumn(name = "TEAM_ID")    // 매핑할 외래키 아름 지정
//    private Team team;

    // 일대다 양방향 매핑을 만들기 위한, 다대일 단방향 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false) // 같은 TEAM_ID 외래키를 관리하므로 읽기 전용으로 설정
    private Team team;

    // ManyToOne과 비슷
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    // 다대다 관계
//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();

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

//    public Team getTeam() {
//        return team;
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }

}
