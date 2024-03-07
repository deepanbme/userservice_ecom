package com.ecommerce.userservice.security.service;

import com.ecommerce.userservice.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@JsonDeserialize(as = CustomGrantAuthority.class)
public class CustomGrantAuthority implements GrantedAuthority {
    private Role role;

    public CustomGrantAuthority() {
    }

    public CustomGrantAuthority(Role role) {
        this.role = role;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return role.getName();
    }
}
