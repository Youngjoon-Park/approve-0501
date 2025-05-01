package com.example.kiosk_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kiosk_backend.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
// JpaRepository를 상속하는 UserRepository라는 인터페이스를 선언
// UserRepository에 메서드를 추가하는 코드
// email 필드를 기준으로 User 엔티티를 조회
// Optional<User>는 조회된 사용자를 담는 컨테이너
// 사용자가 존재하지 않을 경우를 대비해 Optional을 사용
// 사용자가 존재하지 않을 때 null을 반환하는 대신 Optional.empty()를 반환하게 된다
// NullPointException을 방지하고 코드를 안전하게 작성하는 데 도움을 준다
