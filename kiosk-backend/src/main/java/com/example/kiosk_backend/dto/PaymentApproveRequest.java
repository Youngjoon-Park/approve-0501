package com.example.kiosk_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentApproveRequest {
    private String pgToken;
    private Long orderId;
}
