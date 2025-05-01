package com.example.kiosk_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // 기본 생성자 생성 (직렬화 또는 Jackson 사용 시 필요)
@Getter // 모든 필드의 Getter 생성
@Setter // 모든 필드의 Setter 생성
public class AddUserRequest {
    // 회원가입 시 사용자로부터 입력받는 이메일
    private String email;

    // 회원가입 시 사용자로부터 입력받는 비밀번호
    private String password;
}
