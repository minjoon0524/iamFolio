package com.inhatc.portfolio.service;

import com.inhatc.portfolio.entity.Member;
import com.inhatc.portfolio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicationMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicationMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getAddress());

        if(findMember.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }
}
