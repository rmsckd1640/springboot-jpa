package jpabook.jpashop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity //회원 엔티티
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
}
