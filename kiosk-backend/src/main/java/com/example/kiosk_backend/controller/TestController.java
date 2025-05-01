package com.example.kiosk_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kiosk_backend.domain.Member;
import com.example.kiosk_backend.service.TestService;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public List<Member> getAllMembers() {
        List<Member> members = testService.getAllMembers();
        return members;
    }
}// testService.getAllMembers()를 호출하여 모든 회원 정보를 가져온 뒤
 // 해당 정보를 반환합니다.