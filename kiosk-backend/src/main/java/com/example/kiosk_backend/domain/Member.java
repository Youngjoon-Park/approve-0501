package com.example.kiosk_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Member {
    // @Getter 어노테이션은 클래스의 필드에 대한 getter 메서드를 자동으로 생성합니다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // JPA에서 엔티티의 주키를 자동으로 생성할 때 사용
    // strategy 속성을 통해 주키 생성 전략을 지정
    // GenerationType.IDENTITY 전략은 데이터베이스에서 자동으로 주키를 생성하도록 하는 전략
    @Column(name = "id", updatable = false)
    // @Column 어노테이션은 엔티티의 필드와 데이터베이스 테이블의 컬럼 간의 매핑을 지정
    // id 컬럼과 매핑하고 업데이트 불가능하게 설정할 수 있습니다.
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Member(String name) {
        this.name = name;
    }

}
