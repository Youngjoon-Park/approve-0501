package com.example.kiosk_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${kakao.admin-key}")
    private String kakaoAdminKey;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + kakaoAdminKey)
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(10 * 1024 * 1024)) // 10MB 응답 허용
                        .build())
                .build();
    }
}
