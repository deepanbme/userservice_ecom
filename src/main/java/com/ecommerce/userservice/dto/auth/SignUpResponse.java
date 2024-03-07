package com.ecommerce.userservice.dto.auth;

import com.ecommerce.userservice.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponse {
    private String email;
    private Long userId;

    public static SignUpResponse userToSignUpResp(User user){
        return SignUpResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }
}
