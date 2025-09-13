package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mamber_id")
    private Member member; //주문회원

    //cascade는 엔티티의 영속성 상태 변화를 연관된 엔티티에 전파하는 기능이다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //일대일에서는 조회를 많이 하는 곳에 외래키를 두는 것이 좋다.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // import java.time.LocalDateTime;
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING) //열거형 이름 그대로 사용
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    //양방향일때 사용하면 좋다.(편리성, 무결성, 재사용성과 가독성)
    public void setMember(Member member){ //주인인 내가 상대방을 설정한다.
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}