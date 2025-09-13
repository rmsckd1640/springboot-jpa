package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
//값 타입은 변경 불가능하게 설계해야한다.
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //JPA 프레임워크가 동일 패키지 내에서 객체를 생성할 수 있게 하면서도, 외부에서의 무분별한 객체 생성을 막는 역할을 합니다.
    protected  Address(){
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}