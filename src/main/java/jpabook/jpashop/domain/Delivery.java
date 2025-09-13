package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //연관 관계의 주인이 되는 엔티티(Order)에 있는 필드의 이름을 써야 하기 때문
    @OneToOne(mappedBy = "delivery",  fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    //꼭 STRING을 사용하자! 순서 상관 없어짐. 문자열로 반환하여 데이터베이스에 저장하기 때문에
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;




}
