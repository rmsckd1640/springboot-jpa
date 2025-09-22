package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    //의존관계 주입
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
    주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //OrderItem item1 = new OrderItem(); -> @NoArgsConstructor(access = AccessLevel.PROTECTED) 로 막아놨기 때문에 안됨
        //생성로직은 정해져 있는걸로 하면 되는데 다르게, 추가로 해버리면 뭔가 분산되니깐 막아야 함

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        /*
        왜 orderRepository 만 persist 하냐 delivery랑 orderitem은 안하고?
        정답은 order entity에 있다
        order가 persist 될 때 cascade 때문에 모두 강제로 persist 된다.

        어디까지 같이 움직이는게 좋냐?
        orderitem, delivery가 order만 참조하는 이런 경우, 라이프사이클이 똑같은 경우

        만약 orderitem이 중요하고 다른 곳에서 가져다 쓴다? 그럼 cascade 쓰는거 비추
         */

        return order.getId();
    }

    /*
    취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
        /*
        JPA의 아주 큰 장점
        쿼리를 일일이 날릴 필요 없이
        엔티티의 상태 변화가 일어나면
        알아서 다 해준다!
        (영속성 컨텍스트를 통해 엔티티 상태 변화 감지 후 SQL 쿼리 자동 생성 및 실행)
         */
    }

    //검색
    /*
    public List<Order> findOrders(OrderSearch orderSearch){
        return OrderRepository.findAll(orderSearch);
    }

     */
}
