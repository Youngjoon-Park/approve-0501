package com.example.kiosk_backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.kiosk_backend.domain.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void 초기화() {
        em.createQuery("DELETE FROM Member").executeUpdate();
    }

    @Test
    void 엔티티_직접_저장_조회_테스트() {
        Member member = new Member("홍길동");
        em.persist(member); // 저장
        em.flush(); // 즉시 반영
        em.clear(); // 1차 캐시 초기화

        Member found = em.find(Member.class, member.getId()); // 조회
        // System.out.println("🔍 저장된 이름: " + found.getName());
        Assertions.assertEquals(member.getName(), found.getName());
    }
}
