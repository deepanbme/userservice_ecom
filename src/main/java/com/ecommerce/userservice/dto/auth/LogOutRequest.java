package com.ecommerce.userservice.dto.auth;

import lombok.Data;

@Data
public class LogOutRequest {
    private Long userId;
    private String token;
}
