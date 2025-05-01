// 📁 controller/AdminLoginController.java
package com.example.kiosk_backend.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kiosk_backend.entity.AdminUser;
import com.example.kiosk_backend.jwt.JwtTokenProvider;
import com.example.kiosk_backend.repository.AdminUserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://kiosktest.shop", // ✅ 추가
        "https://kiosktest.shop",
        "http://3.38.6.220:8081" // ✅ 추가!!
})

public class AdminLoginController {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        // ✅ 여기에 로그 추가
        System.out.println("📥 email = " + email);
        System.out.println("📥 password = " + password);

        AdminUser admin = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("❌ 관리자 계정이 없습니다."));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            return ResponseEntity.badRequest().body("❌ 비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(admin.getEmail(), admin.getRole());

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
