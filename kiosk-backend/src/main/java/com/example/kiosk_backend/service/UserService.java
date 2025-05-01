package com.example.kiosk_backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.kiosk_backend.domain.User;
import com.example.kiosk_backend.dto.AddUserRequest;
import com.example.kiosk_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// final 키워드 또는 @NonNull 어노테이션이 붙은 필드만 파라미터로 받는 생성자를 자동으로 생성해줍니다
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {// 사용자 정보를 저장하는 메서드
        if (dto.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        // 사용자 정보를 생성된 User 객체를 Spring Data JPA의 Repository를 이용해 데이터베이스에 저장한다
        return userRepository.save(User.builder()
                // User 클래스에 정의된 빌더 패턴을 시작합니다
                .email(dto.getEmail())
                // dto에서 제공받은 이메일을 User 객체의 이메일 필드에 설정합니다.
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                // dto에서 제공받은 비밀번호를 BCrypt 알고리즘을 사용하여 암호화하고,
                // 암호화된 비밀번호를 User 객체의 비밀번호 필드에 설정합니다.
                .auth("user") // 👈 필요 시 권한 필드도 추가
                .build()).getId();
    }// 빌더를 통해 User 객체를 생성합니다, 저장된 User 객체에서 ID를 가져옵니다
}
