package com.example.kiosk_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoReadyResponse {
    private String tid;

    @JsonProperty("next_redirect_mobile_url")
    private String nextRedirectMobileUrl;
}
