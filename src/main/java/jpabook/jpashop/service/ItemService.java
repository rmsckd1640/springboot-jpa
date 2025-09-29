package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//상품 서비스는 상품 리포지토리에 단순히 위임만 하는 클래스
//서비스 계층에서 트랜잭션 관리
@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    /*
    준영속 엔티티 수정 방법 1: 변경 감지 기능 사용
     */
    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity){
        //findItem이 영속상태인 이유는 @Transactional 어노테이션이 붙은 매서드 안에서
        //데이터베이스로부터 직접 조회된 엔티티이기 때문.
        Item item = itemRepository.findOne(id);
        //영속상태인 findItem 객체의 데이터를 메모리에서 수정
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity); //파라미터가 많으면 DTO를 만들어라
        //트랜잭션을 성공적으로 마치고 커밋을 하기 전 DB와 동기화하기 위해 플러시를 호출
        //변경 감지후 변경 부분 내용에 맞는 sql 쿼리 자동 생성
        //생성된 sql 쿼리를 DB에 전송
        //플러시가 끝나면 DB에 커밋 명령이 실행되어 변경사항 영구 저장
    }

    /*
    준영속 엔티티 수정 방법 2: 병합 사용 -> merge()
    주의: 병합을 사용하면 모든 속상이 변경되기 때문에 값이 없으면 null로 업데이트 할 위험이 있다.
    (병합은 모든 필드를 교체한다)

    변경 감지 기능은 원하는 속성만 선택해서 변경 가능!! (병합과의 차이점)

    엔티티를 변경할 때는 항상 변경 감지를 사용하자!!!

    실무에서는 set 막 갈기지 말고 의미있는 메서드 만들자!
     */


    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
