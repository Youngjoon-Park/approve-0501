package com.example.kiosk_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kiosk_backend.domain.Member;
import com.example.kiosk_backend.repository.MemberRepository;

@Service
public class TestService {
    @Autowired
    MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}// getAllMembers 메서드를 통해 모든 회원 정보를 가져오는 기능
 // findAll() 메서드를 호출하여 모든 회원 정보를 조회합니다