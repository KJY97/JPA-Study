package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberProduct {

    // 비식별 관계 -> Member와 Product는 외래키로 그냥 사용하고 비즈니스와 전혀 상관없는 식별자 만들기
    // Member와 Product를 복합키로 묶어서 사용해도 되지만(식별 관계) 코드가 복잡해지므로 비추천한다.
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int count;
    private int price;

    private LocalDateTime orderDateTime;

}
