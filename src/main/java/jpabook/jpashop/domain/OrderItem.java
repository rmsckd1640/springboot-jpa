package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성메서드 외에는 생성 못하게 막아둠
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //주문상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); //주문된 수량 만큼 재고에서 빼줘야 함

        return orderItem;
    }


    //==비즈니스 로직==//
    public void cancel(){
        //취소할 아이템의 수량을 주문했던 수량 즉 count만큼 다시 추가해야함
        getItem().addStock(count);
    }

    //해당 상품 총 주문 가격 로직 (하나든 여러개든)
    //==조회 로직==//
    /*
    주문상품 전체 가격 조회
     */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
        /*
        orderPrice 말고 getter 메서드를 쓰는 이유 캡슐화
        캡슐화는 데이터와 그 데이터를 다루는 메서드를 하나의 객체 안에 묶어서 보호하는 것
        같은 클래스 내부에서도 "객체는 자신의 데이터를 메서드를 통해 다룬다"는 원칙을 지키고
        복잡한 로직이 추가될 때 코드의 일관성과 유지보수성을 높이기 위함

        예시 : 할인 정책
         */
    }
}