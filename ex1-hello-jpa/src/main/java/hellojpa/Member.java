package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 반드시 넣어줘야 한다! 그래야 처음 jpa가 로딩될 때 인식 가능함
//@Table(name = "USER") // 만약 DB의 memeber이름이 User라면 이렇게 지정해준다.
public class Member {

    @Id // PK가 무엇인지 선언
    private Long id;

//    @Column(name = "username") // DB의 컬럼 이름이 username이라면 지정해준다.
    private String name;

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
}
