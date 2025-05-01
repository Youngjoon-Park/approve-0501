package com.example.kiosk_backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String adminPassword = "admin1234";
        String userPassword = "user1234";
        String testPassword = "1234";

        System.out.println("✅ admin1234 해시: " + encoder.encode(adminPassword));
        System.out.println("✅ user1234 해시: " + encoder.encode(userPassword));
        System.out.println("✅ 1234 해시: " + encoder.encode(testPassword));
    }
}
