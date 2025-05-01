package com.example.kiosk_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kiosk_backend.domain.Member;
import com.example.kiosk_backend.repository.MemberRepository;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public void test() {
        // memberRepository.save(new Member(1L, "A"));
        memberRepository.save(new Member("A")); // ID는 자동 생성되므로 생략

        Optional<Member> member = memberRepository.findById(1L);
        List<Member> allMembers = memberRepository.findAll();

        memberRepository.deleteById(1L);
    }
}