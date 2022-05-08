package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 고아객체 관리 orphanRemoval = true
    // CascadeType.ALL 영속성 전이 모두 적용(영속, 삭제, 병합)
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    // ==== 연관관계 메소드 ====
    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
