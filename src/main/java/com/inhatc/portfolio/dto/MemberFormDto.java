package com.inhatc.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFormDto {
    // validation 어노테이션 추가
    @NotBlank(message = "이름은 필수 항목 입니다.")  // NULL체크 및 문자열의 경우 길이 0 및 빈문자열(" ") 인지 검사
    private String name;

    @NotEmpty(message = "이메일은 필수 항목 입니다.")  // NULL체크 및 문자열의 경우 길이 0 인지 검사
    @Email(message = "이메일 형식이 올바르지 않습니다.")  // 이메일 형식이 올바른지 검증한다.

    private String email;

    @NotEmpty(message = "비밀번호는 필수 항목 입니다.")
    @Length(min = 4, max = 12, message = "비밀번호는 최소 4자, 최대 12자를 입력해주세요.")
    private String password;

    @NotEmpty(message = "주소는 필수 항목 입니다.")
    private String address;
}
