package com.example.kiosk_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdminLoginRequest {
    private String email;
    private String password;
}
