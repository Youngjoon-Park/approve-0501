// 📁 com.example.kiosk_backend.controller.UserApiController.java

package com.example.kiosk_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kiosk_backend.dto.AddUserRequest;
import com.example.kiosk_backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // ✅ JSON 응답을 반환하는 API 전용 컨트롤러
@RequestMapping("/api/users") // ✅ API URL prefix
public class UserApiController {

    private final UserService userService;

    // ✅ 회원가입 요청 처리 (React에서 POST 전송)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AddUserRequest request) {
        userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    // ✅ 로그아웃은 클라이언트에서 토큰 삭제 또는 세션 만료로 처리 (프론트에서 대응)
    // 서버 측에서 특별한 작업이 없다면 별도 핸들러 없이도 가능
}
