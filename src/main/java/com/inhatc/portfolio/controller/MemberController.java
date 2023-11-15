package com.inhatc.portfolio.controller;

import com.inhatc.portfolio.dto.MemberFormDto;
import com.inhatc.portfolio.entity.Member;
import com.inhatc.portfolio.service.MemberService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/member/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping("/member/new")
    public String saveMember(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()) {     // 입력값 검증 결과 에러가 있을 경우
            return "member/memberForm";     // 회원가입 페이지로 이동
        }

        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);    // 회원가입 정보를 Member 객체로 변환
            memberService.saveMember(member);                                       // 회원가입 정보를 DB에 저장
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";

    }


}
