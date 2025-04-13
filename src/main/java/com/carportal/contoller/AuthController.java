package com.carportal.contoller;

import com.carportal.entity.User;
import com.carportal.payload.LoginDto;
import com.carportal.repository.UserRepository;
import com.carportal.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private UserRepository userRepository;
    private AuthService authService;

    public AuthController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }
@PostMapping("/user/signup")
    public ResponseEntity<String> createUser(
            @RequestBody User user
    ){
        Optional<User> opEmail=userRepository.findByEmailId(user.getEmailId());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email id already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile.isPresent()){
            return new ResponseEntity<>("Mobile number already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opUserName = userRepository.findByUsername(user.getUsername());
        if(opUserName.isPresent()){
            return new ResponseEntity<>("Username already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(4));
        user.setPassword(hashpw);  // Hash the password before saving it in the database.

       // Ensure ID is null to allow auto-generation
         user.setId(null);
         user.setRole("ROLE_USER");
        userRepository.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);

    }@PostMapping("/owner/signup")
    public ResponseEntity<String> createOwner(
            @RequestBody User user
    ){
        Optional<User> opEmail=userRepository.findByEmailId(user.getEmailId());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email id already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile.isPresent()){
            return new ResponseEntity<>("Mobile number already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opUserName = userRepository.findByUsername(user.getUsername());
        if(opUserName.isPresent()){
            return new ResponseEntity<>("Username already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(4));
        user.setPassword(hashpw);  // Hash the password before saving it in the database.

        // Ensure ID is null to allow auto-generation
        user.setId(null);
        user.setRole("ROLE_Owner");
        userRepository.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);

    }
    
    @PostMapping("/login")
    public ResponseEntity<String> verifyLogin(
            @RequestBody LoginDto loginDto
    ){
        String jwtToken = authService.authenticateUser(loginDto);
        if(jwtToken!=null){
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    }


