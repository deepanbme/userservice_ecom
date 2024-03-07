package com.ecommerce.userservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRoleDto {
    private String roleName;
    private Long roleId;
}
