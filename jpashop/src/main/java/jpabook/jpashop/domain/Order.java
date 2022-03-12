package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

//    @Column(name = "MEMBER_ID")
//    private Long memberId;

    // 설계할 때는 단방향으로!
    // 가급적 관계는 단방향이 좋다.
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; // 객체 지향스럽게!

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // 주문과 배송 관계 중 주테이블은 주문
    // 연관관계의 주인이다.
    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery; // 배송정보

    // 자바 8부터는 자동 매핑됨
    // spring boot에서는 카멜 스타일을 자동으로 데이터베이스 컬럼 규칙 형태(order_date)와 연결해준다.
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Orderstatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(Long memberId) {
//        this.memberId = memberId;
//    }

    public Member getMember() {
        return member;
    }

    public void changeMember(Member member) {
        if(this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Orderstatus getStatus() {
        return status;
    }

    public void setStatus(Orderstatus status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItems(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
