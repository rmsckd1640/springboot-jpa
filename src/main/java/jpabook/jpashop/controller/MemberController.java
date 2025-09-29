package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createFrom(Model model){
        model.addAttribute("memberForm", new MemberForm()); //빈 껍데기를 들고감
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    //클라이언트가 보내온 데이터를 MemberForm객체로 변환한 후 그 안에 선언된 제약 조건들을 기반으로 유효성 검증
    //검증 실패시 기본적으로 예외 발생, 하지만 BindingResult와 함께 사용하여 예외 방지 및 검증 결과 받을 수 있음 (회원가입시 "이름을 입력하세요"와 같은 메시지)
    public String create(@Valid MemberForm form, BindingResult result){ //오류 검증하고 오류있으면 리턴 (MemberForm에서의 NotEmpty)

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; //첫번째 페이지로 넘어감
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

//api를 만들 때 절대! 웹에 반환하지말것
/*
엔티티 필드가 추가, 삭제, 변경될 때마다 영향을 받음 -> API 스펙이 불안정해짐
민감한 정보 노출될 위험 증가.
따라서 API 응답은 DTO 등으로 변환하여 전달하는 것이 좋다.
 */