package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
//이 설정은 상속 관계에 있는 여러 엔티티(클래스)를 데이터베이스의 단일 테이블로 합쳐서 관리하는 전략을 의미
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//JPA는 각 레코드가 어떤 자식 클래스에 속하는지 구분하기 위한 특별한 컬럼(기본적으로 DTYPE)을 자동으로 추가
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
//Item은 추상적인 개념. 진짜 상품들을 구현하면 됨
public abstract class Item {//상속관계 매핑해야함
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}