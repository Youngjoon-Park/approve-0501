// 📁 src/main/java/com/example/kiosk_backend/service/PaymentService.java
package com.example.kiosk_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.kiosk_backend.dto.KakaoReadyResponse;
import com.example.kiosk_backend.entity.Order;
import com.example.kiosk_backend.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // ✅ 이제 이게 제대로 작동함!
public class PaymentService {

    private final WebClient webClient;
    private final OrderRepository orderRepository;

    private String tid;

    @Value("${kakao.cid}")
    private String kakaoCid;

    @Value("${kakao.approve-url}")
    private String kakaoApproveUrl;

    @Value("${kakao.cancel-url}")
    private String kakaoCancelUrl;

    @Value("${kakao.fail-url}")
    private String kakaoFailUrl;

    public KakaoReadyResponse kakaoPayReady(Long orderId, int totalAmount) {
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("❌ 총 결제 금액이 0원 이하입니다.");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("❌ 주문 ID가 유효하지 않습니다."));

        String itemName = order.getSummary();
        if (itemName == null || itemName.trim().isEmpty()) {
            itemName = "주문내역 없음";
        } else {
            itemName = itemName.replaceAll("[^가-힣a-zA-Z0-9\\s]", "");
        }

        if (itemName.length() > 100) {
            itemName = itemName.substring(0, 100);
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", kakaoCid);
        params.add("partner_order_id", "order_" + orderId);
        params.add("partner_user_id", "user_" + orderId);
        params.add("item_name", itemName);
        params.add("quantity", "1");
        params.add("total_amount", String.valueOf(totalAmount));
        params.add("tax_free_amount", "0");
        params.add("approval_url", kakaoApproveUrl);
        params.add("cancel_url", kakaoCancelUrl);
        params.add("fail_url", kakaoFailUrl);

        System.out.println("✅ 카카오 요청 파라미터: " + params);

        KakaoReadyResponse response = webClient.post()
                .uri("/v1/payment/ready")
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(KakaoReadyResponse.class)
                .doOnError(error -> {
                    System.out.println("❌ 결제 준비 오류:");
                    error.printStackTrace();
                })
                .block();

        this.tid = response.getTid();
        return response;
    }

    public void kakaoPayApprove(String pgToken, Long orderId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", kakaoCid);
        params.add("tid", this.tid);
        params.add("partner_order_id", "order_" + orderId);
        params.add("partner_user_id", "user_" + orderId);
        params.add("pg_token", pgToken);

        webClient.post()
                .uri("/v1/payment/approve")
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    System.out.println("❌ 결제 승인 오류:");
                    error.printStackTrace();
                })
                .block();
    }
}
