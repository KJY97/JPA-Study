package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;

    // 주문과 배달의 연관관계에서 주인이 아님을 표시
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
}
