package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null){ //id가 없으면 신규로 보고 persist() 실행
            em.persist(item);
        } else{
            em.merge(item);
            /*
            JPA는 준영속 객체의 식별자를 이용하여 1차 캐시에 이미 있는지 확인 (없다면 select 쿼리를 실행해 1차 캐시에 넣음)
            이 과정을 통해 영속 상태의 원본 엔티티 확보
            이 원본 엔티티(영속)에 그대로 덮어씀
            이 영속 상태의 객체를 반환
             */
        }
        /*
        merge()는 새로운 영속 상태의 엔티티를 반환합니다.
        사용자가 웹 페이지에서 상품 정보를 수정한다 가정해보면
        서비스 계층에서 id를 통해 DB에서 상품을 조회하고 이를 화면으로 보낸다.
        사용자가 상품 정보를 수정하고 저장 버튼을 누르면 수정된 정보가 다시 서버로 넘어온다.
        이때 서버로 넘어온 객체는 DB에서 조회된 원래 엔티티와는 다른, 새롭게 생성된 객체 (준영속 상태) 이다.
        이 준영속 객체를 영속성 컨텍스트에 다시 포함시키고 변경 내용을 반영하기 위해 merge()를 사용한다.
         */
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}


/*
EntityManager를 관리하는 방식
1. jpa 설정 감지
2. EntityManagerFactory 객체 자동 생성
3. EntityManager 프록시 주입
이 프록시는 개발자가 em.persist()같은 메서드를 호출하면 현재 진행 중인
트랜잭션에 맞는 EntityManager를 대신 찾아와서 작업을 수행해준다.
덕분에 개발자는 트랜잭션 범위와 쓰레드 안정성에 대해 신경 쓸 필요없이 편하게 EntityManager 사용가능
 */