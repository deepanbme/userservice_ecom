package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.auth.LoginRequest;
import com.ecommerce.userservice.exception.UserAlreadyExistException;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserDetails(Long userId){
        Optional<User> userData = userRepository.findById(userId);
        if(userData.isEmpty()){
            throw new UserNotFoundException("User details not found");
        }

        return userData.get();
    }

    public User setUserRoles(Long userId, List<Long> roleId){
        Optional<User> userData = userRepository.findById(userId);
        if(userData.isEmpty()){
            throw new UserNotFoundException("User details not found");
        }

        User user = userData.get();
        user.setRoles(Set.copyOf(roleService.getRolesIn(roleId)));
        return userRepository.save(user);
    }

    public User createUser(LoginRequest request){
        Optional<User> userExists = userRepository.findByEmail(request.getEmail());

        if(userExists.isPresent()){
            throw new UserAlreadyExistException("User email exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public User getUserByEmail(String email){
        Optional<User> userData = userRepository.findByEmail(email);
        if(userData.isEmpty()){
            throw new UserNotFoundException("User details not found");
        }

        return userData.get();
    }
}
