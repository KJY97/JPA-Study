package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    private Member member; // 객체 지향스럽게!

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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
}
