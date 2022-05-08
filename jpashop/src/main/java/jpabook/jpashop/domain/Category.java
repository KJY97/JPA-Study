package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    // ==== 카테고리 밑에 또 다른 카테고리를 나타내기 위한 코드 ====
    // 계층구조를 위한 코드들
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"), // 내가 조인하려는 컬럼
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID") // 반대족에서 조인하려는 컬럼
    )
    private List<Item> items = new ArrayList<>();
}
