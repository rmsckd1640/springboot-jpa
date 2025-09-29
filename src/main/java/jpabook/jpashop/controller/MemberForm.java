package jpabook.jpashop.controller;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm { //DTO (Data Transfer Object)

    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}

/*
엔티티는 화면을 위한 로직은 없어야한다 -> 이건 DTO
엔티티는 최대한 순수하게 유지. (핵심 비즈니스 로직에만 집중)
 */