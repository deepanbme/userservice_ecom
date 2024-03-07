package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.role.CreateRoleResponse;
import com.ecommerce.userservice.model.Role;

public class RoleMapper {
    public static CreateRoleResponse roleToCreateRoleResponse(Role role){
        return CreateRoleResponse.builder()
                .roleId(role.getId())
                .build();
    }
}
