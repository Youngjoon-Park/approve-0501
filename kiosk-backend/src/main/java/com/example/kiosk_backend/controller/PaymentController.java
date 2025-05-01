// 📁 src/main/java/com/example/kiosk_backend/controller/PaymentController.java
package com.example.kiosk_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kiosk_backend.dto.KakaoReadyResponse;
import com.example.kiosk_backend.dto.PaymentRequest;
import com.example.kiosk_backend.entity.Order;
import com.example.kiosk_backend.repository.OrderRepository;
import com.example.kiosk_backend.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderRepository orderRepository;

    // ✅ 1. 결제 준비 요청 API
    @PostMapping("/api/payment/ready")
    public KakaoReadyResponse readyToPay(@RequestBody PaymentRequest paymentRequest) {
        if (paymentRequest == null || paymentRequest.getOrderId() == null) {
            throw new IllegalArgumentException("❌ orderId가 요청 본문에 포함되지 않았습니다.");
        }

        Long orderId = paymentRequest.getOrderId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("❌ 해당 주문 ID가 존재하지 않습니다."));

        // ✅ 총 결제 금액 계산 (메뉴 가격 * 수량)
        int totalAmount = order.getItems().stream()
                .mapToInt(item -> item.getMenu().getPrice() * item.getQuantity())
                .sum();

        System.out.println("✅ 서버에서 계산한 totalAmount: " + totalAmount);

        // ✅ 카카오페이 결제 준비 요청
        return paymentService.kakaoPayReady(orderId, totalAmount);
    }

    // ✅ 2. 결제 승인 요청 API
    @PostMapping("/api/payment/approve")
    public ResponseEntity<String> approvePayment(@RequestBody PaymentRequest paymentRequest) {
        String pgToken = paymentRequest.getPgToken();
        Long orderId = paymentRequest.getOrderId();

        if (pgToken == null || orderId == null) {
            return ResponseEntity.badRequest().body("❌ 필수 값 누락(pgToken 또는 orderId)");
        }

        paymentService.kakaoPayApprove(pgToken, orderId);
        return ResponseEntity.ok("✅ 결제 승인 완료");
    }

}
