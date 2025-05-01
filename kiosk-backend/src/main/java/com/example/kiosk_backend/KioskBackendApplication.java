package com.example.kiosk_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.kiosk_backend") // ✅ 수정
public class KioskBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(KioskBackendApplication.class, args);
	}

}
