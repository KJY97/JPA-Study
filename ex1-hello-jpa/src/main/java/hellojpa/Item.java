package hellojpa;

import javax.persistence.*;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED) // 매핑 전략을 조인 전략으로 설정
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략으로 설정
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 구현 클래스마다 테이블 전략으로 설정
//@DiscriminatorColumn(name = "DTYPE") // 단일 테이블에서는 반드시 필요한 구분 컬럼
public abstract class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
