package com.example.kiosk_backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheckTest {
    public static void main(String[] args) {
        String rawPassword = "1234";
        String encodedPassword = "$2a$10$VvCFs5pBdkkjFSZb4k6Z7OG6A4pKkAChH1ZWADhJ4yFJSbGOebSxm";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean result = encoder.matches(rawPassword, encodedPassword);

        System.out.println("✅ 암호 일치 여부: " + result); // true 나와야 정상
    }
}
