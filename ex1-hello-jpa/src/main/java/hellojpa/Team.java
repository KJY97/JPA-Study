package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    // 무엇과 연결되어 있는지를 작성해주는 것. (변수명으로 작성)
    // 즉, team에 의해 관리되고 있다는 것을 말한다.(team이 연관관계의 주인)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>(); // 관례: 반드시 ArrayList로 초기화 해준다.

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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
