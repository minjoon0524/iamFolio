package com.inhatc.portfolio.entity;

import com.inhatc.portfolio.constant.Role;
import com.inhatc.portfolio.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;


    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member=Member.builder()
                .role(Role.USER)
                .email(memberFormDto.getEmail())
                .address(memberFormDto.getAddress())
                .name(memberFormDto.getName())
                .password(passwordEncoder.encode(memberFormDto.getPassword()))
                .build();

        return member;

    }
}
