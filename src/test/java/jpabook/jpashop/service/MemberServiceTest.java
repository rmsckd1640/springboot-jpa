package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //스프링부트와 동일한 환경으로 테스트 가능
@Transactional
//트랜잭션의 역할
// 메서드가 호출되면 트랜잭션이 시작된다. 영속성 컨텍스트에 저장되고, 메서드가 성공적으로 끝나면 트랜잭션이 커밋되면서 데이터가 데이터베이스에 반영된다.
// 커밋될 때까지 데이터베이스에 변경 사항을 반영하지 않고 영속성 컨텍스트라는 메모리 공간에만 엔티티를 저장한다.
// @Test와 함께 사용되면 테스트가 끝난 후 자동으로 모든 변경 사항을 롤백한다.(콘솔을 보면 insert문이 없다)
// 참고: 플러시란 플러시가 발생하면 영속성 컨텍스트에 쌓여 있던 변경 내용들이 하나의 SQL 쿼리로 만들어져 데이터베이스로 보내진다.
    // JPA는 성능을 위해 트랜잭션이 커밋되기 전까지는 엔티티의 변경 내용을 데이터베이스에 바로 반영하지 않고
    // 변경 내용을 영속성 컨텍스트라는 임시 메모리 공간에 모아둔다.
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member(); //멤버 생성
        member.setName("kim"); //멤버 이름 주입

        Long saveId = memberService.join(member); //생성한 멤버로 회원가입

        assertEquals(member, memberRepository.findOne(saveId)); //생성된 멤버와 저장된 멤버가 같은지 확인
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);

        //IllegalStateException 예외가 발생하지 않으면 테스트 실패
        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }
}