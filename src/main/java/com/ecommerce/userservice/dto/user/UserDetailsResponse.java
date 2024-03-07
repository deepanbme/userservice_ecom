package com.ecommerce.userservice.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDetailsResponse {
    private String email;
    private List<String> userRole;
}
