package com.ecommerce.userservice.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleSetUpRequest {
    private List<Long> roleIds;
}
