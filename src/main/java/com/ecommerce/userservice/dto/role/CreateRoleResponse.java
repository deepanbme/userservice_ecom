package com.ecommerce.userservice.dto.role;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CreateRoleResponse {
    private Long roleId;
}
