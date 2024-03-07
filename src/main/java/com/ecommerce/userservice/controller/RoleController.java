package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.role.CreateRoleRequest;
import com.ecommerce.userservice.dto.role.CreateRoleResponse;
import com.ecommerce.userservice.mapper.RoleMapper;
import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<CreateRoleResponse> createRole(@RequestBody CreateRoleRequest request){
        Role role = roleService.createRole(request.getRoleName());
        CreateRoleResponse response = RoleMapper.roleToCreateRoleResponse(role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
