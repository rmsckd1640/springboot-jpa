package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

//레포지토리는 DB와 상호작용
@Repository //컴포넌트 스캔에 의해서 자동으로 스프링 빈으로 관리
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //엔티티 매니저 주입받기
    //스프링 데이터 JPA를 사용하여 주입받기 -> 스프링 컨테이너가 의존성을 주입하는 일관된 방식을 유지
    // -> final 키워드를 사용하기 때문에, 테스트 코드를 작성할 때 스프링 컨테이너 없이도 직접 생성자를 호출하여 EntityManager를 주입
    // -> 불변성 보장
    private final EntityManager em;

    //사용자 저장
    public void save(Member member) {
        em.persist(member);
    }

    //사용자 한명 조회
    public Member findOne(Long id) { //단건 조회
        return em.find(Member.class, id);
    }

    //리스트 조회, 사용자 여러명 조회
    public List<Member> findAll() { //jpql
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); //jpql은 엔티티 객체에 대한 조회
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
