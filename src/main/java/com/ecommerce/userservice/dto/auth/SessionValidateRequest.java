package com.ecommerce.userservice.dto.auth;

import lombok.Data;

@Data
public class SessionValidateRequest {
    private Long userId;
    private String token;
}
