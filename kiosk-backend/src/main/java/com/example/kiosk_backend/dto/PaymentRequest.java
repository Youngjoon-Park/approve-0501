package com.example.kiosk_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long orderId;
    private String pgToken; // ✅ 결제 승인용 토큰 필드 추가
}
