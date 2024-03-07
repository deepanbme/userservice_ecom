package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.user.UserDetailsResponse;
import com.ecommerce.userservice.dto.user.UserRoleSetUpRequest;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable("userId") Long userId){
        User user = userService.getUserDetails(userId);
        UserDetailsResponse response = UserMapper.userRoleSetUpRequestToUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<UserDetailsResponse> setUserRoles(@RequestBody UserRoleSetUpRequest userRoleSetUpRequest, @PathVariable("userId") Long userId){
        User user = userService.setUserRoles(userId, userRoleSetUpRequest.getRoleIds());
        UserDetailsResponse response = UserMapper.userRoleSetUpRequestToUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
