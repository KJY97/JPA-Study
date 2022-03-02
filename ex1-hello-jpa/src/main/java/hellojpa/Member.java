package hellojpa;

import javax.persistence.*;

@Entity // 반드시 넣어줘야 한다! 그래야 처음 jpa가 로딩될 때 인식 가능함
//@Table(name = "USER") // 만약 DB의 memeber이름이 User라면 이렇게 지정해준다.
public class Member {

    /**
     * 엔티티 매팅까지 활용
    @Id // PK가 무엇인지 선언
    private Long id;

//    @Column(name = "username") // DB의 컬럼 이름이 username이라면 지정해준다.
    private String name;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    */

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    // 객체 관계 매핑!
    @ManyToOne // Member 입장에서 Many, Team 입장에서는 one
    @JoinColumn(name = "TEAM_ID")    // 매핑할 외래키 아름 지정
    private Team team;

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

//    public Long getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }

    public Team getTeam() {
        return team;
    }

//    public void setTeam(Team team) {
//        this.team = team;
//    }

    // 연관관게 편의 메소드
    // 연관관계를 맺는 메소드이기 때문에 특별?해 보이기 위해 change로 변경
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
