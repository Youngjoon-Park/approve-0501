// 📁 src/main/java/com/example/kiosk_backend/config/WebMvcConfig.java
package com.example.kiosk_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // uploads 폴더를 외부에 공개
        registry.addResourceHandler("/uploads/**")
                // .addResourceLocations("file:///C:/uploads/"); // ✅ 3개의 슬래시!
                .addResourceLocations("file:/home/ubuntu/kiosk-system/uploads/"); // ✅ 실제 서버 경로
    }
}
