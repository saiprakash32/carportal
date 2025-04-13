package com.carportal.service;

import com.carportal.entity.User;
import com.carportal.payload.LoginDto;
import com.carportal.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    AuthService(UserRepository userRepository, JWTService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String authenticateUser(
            LoginDto loginDto
    ) {
        Optional<User> oplUser = userRepository.findByUsername(loginDto.getUsername());
        if (oplUser.isPresent()) {
            User user = oplUser.get();
            boolean status = BCrypt.checkpw(loginDto.getPassword(), user.getPassword());// row password & encryption
            if (status) {
                String s = jwtService.generateToken(user.getUsername());
                return s;


            }
        }
        return null;

    }
}
