package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.user.UserDetailsResponse;
import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.model.User;

import java.util.List;

public class UserMapper {
    public static UserDetailsResponse userRoleSetUpRequestToUser(User user){
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();
        return UserDetailsResponse.builder()
                .email(user.getEmail())
                .userRole(roles)
                .build();
    }
}
