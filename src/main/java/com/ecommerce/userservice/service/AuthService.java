package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.auth.*;
import com.ecommerce.userservice.dto.user.UserDetailsResponse;
import com.ecommerce.userservice.exception.InvalidSessionException;
import com.ecommerce.userservice.exception.InvalidUserCredentialsException;
import com.ecommerce.userservice.exception.SessionLimitReachedException;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.model.Session;
import com.ecommerce.userservice.model.SessionStatus;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.SessionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthService {
    private UserService userService;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignUpResponse signUp(LoginRequest loginRequest){
        User user = userService.createUser(loginRequest);
        return SignUpResponse.userToSignUpResp(user);
    }

    public ResponseEntity<UserDetailsResponse> login(LoginRequest request){
        User user = userService.getUserByEmail(request.getEmail());

        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidUserCredentialsException("Invalid user credentials");
        }

        //validate if number of active sessions are less than 3
        Optional<List<Session>> activeSessionOptional = sessionRepository.findAllByUserAndSessionStatus(user, SessionStatus.ACTIVE);
        if(!activeSessionOptional.isEmpty() && activeSessionOptional.get().size() == 2){
            throw new SessionLimitReachedException("Please logout any one of active session");
        }



//        String token = String.valueOf(new Date().getTime());

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();


        //start adding the claims
        Map<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("userId", user.getId());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("createAt", new Date());
        jsonForJwt.put("expiryAt", Date.from(LocalDateTime.now().plusMinutes(3).atZone(ZoneId.systemDefault()).toInstant()));

        String token = Jwts.builder()
                .claims(jsonForJwt)
                .signWith(key, alg)
                .compact();

        Session session = new Session();
        session.setLoginAt(new Date());
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        session.setExpiringAt((Date) jsonForJwt.get("expiryAt"));
        sessionRepository.save(session);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, token);

        UserDetailsResponse response = UserMapper.userRoleSetUpRequestToUser(user);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public ResponseEntity<Void> logout(Long userId, String token){
        Optional<Session> session = sessionRepository.findByTokenAndUser_Id(token, userId);

        if(session.isEmpty()){
            throw new InvalidUserCredentialsException("Invalid session");
        }

        validateIfTokenExpired(token);

        session.get().setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public SessionResponseDto validateToken(Long userId, String token) throws JsonProcessingException {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

//        String header = new String(decoder.decode(chunks[0]));

        Optional<Session> session = sessionRepository.findByTokenAndUser_Id(token, userId);
        if(session.isEmpty()){
            throw new InvalidSessionException("Invalid session");
        }

        String payload = new String(decoder.decode(chunks[1]));

        ObjectMapper objectMapper = new ObjectMapper();
        JwtMapper jwtMapper = objectMapper.readValue(payload, JwtMapper.class);
        User user1 = userService.getUserDetails(userId);
        User user2 = userService.getUserDetails(jwtMapper.getUserId());

        if(!Objects.equals(user1.getId(), user2.getId())){
            throw new InvalidSessionException("Invalid token");
        }

//        if(session.get().getSessionStatus().equals(SessionStatus.ENDED) || new Date().compareTo(session.get().getExpiringAt()) >= 0){
//            throw new SessionExpiredException("Session has expired! Please login again");
//        }
        SessionResponseDto sessionResponseDto = new SessionResponseDto();
        sessionResponseDto.setSession(SessionStatus.ACTIVE.toString());
        return sessionResponseDto;
    }

    private void validateIfTokenExpired(String token){
        Session session = sessionRepository.findSessionByToken(token).get();
        if(new Date().compareTo(session.getExpiringAt()) >= 0){
            throw new RuntimeException("Token expired");
        }

    }
}
