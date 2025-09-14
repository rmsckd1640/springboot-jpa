package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //변경 감지 안함, 불필요한 플러시 방지 등
//트랜잭션은 데이터베이스에서 더 이상 나눌 수 없는 하나의 작업 단위
//트랜잭션을 사용하지 않으면 데이터 불일치 문제 발생할 수 있음
//모든 과정을 하나의 트랜잭션으로 묶어줌. 모든 작업 성공 -> 영구적 반영, 하나라도 실패 -> 롤백
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional //얘는 변경해야하니 얘만 따로 readOnly없이
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member); //회원 저장하고
        return member.getId(); //뭐가 저장됐는지 알기 위해
    }

    //예외 처리
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}