package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany//실무에서는 사용하지 말자. 다대다 매핑을 일대다, 다대일 매핑으로 풀어내서 사용하자
    //@ManyToMany 애노테이션을 사용하면, JPA가 두 엔티티를 연결하기 위해 중간에 조인(Join) 테이블을 자동으로
    //생성합니다. 이 과정에서 개발자가 직접 조인 테이블을 제어할 수 없게 되면서 다음과 같은 문제가 발생합니다.
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items =  new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
