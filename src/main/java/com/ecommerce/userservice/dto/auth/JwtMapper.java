package com.ecommerce.userservice.dto.auth;

import com.ecommerce.userservice.model.Role;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
public class JwtMapper {
    private Long userId;
    private Set<Role> roles;
    private Date createAt;
    private Date expiryAt;
}
