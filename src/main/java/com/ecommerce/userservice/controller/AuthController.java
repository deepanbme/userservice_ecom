package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.auth.LoginRequest;
import com.ecommerce.userservice.dto.auth.SessionResponseDto;
import com.ecommerce.userservice.dto.auth.SessionValidateRequest;
import com.ecommerce.userservice.dto.auth.SignUpResponse;
import com.ecommerce.userservice.dto.user.UserDetailsResponse;
import com.ecommerce.userservice.model.SessionStatus;
import com.ecommerce.userservice.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDetailsResponse> login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody LoginRequest request){
        SignUpResponse response = authService.signUp(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<Void> logOut(@PathVariable("userId") Long userId, @RequestHeader("auth-token") String token){
        return authService.logout(userId, token);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionResponseDto> validateToken(@RequestBody SessionValidateRequest request) throws JsonProcessingException {
        SessionResponseDto sessionStatus = authService.validateToken(request.getUserId(), request.getToken());
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }
}
