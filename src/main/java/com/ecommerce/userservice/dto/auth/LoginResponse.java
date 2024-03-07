package com.ecommerce.userservice.dto.auth;

import com.ecommerce.userservice.model.Session;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponse {
    private String email;
    private String userId;
    private List<Session> sessions;
}
