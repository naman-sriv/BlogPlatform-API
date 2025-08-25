package com.learning.blogplatformapi.controller;

import com.learning.blogplatformapi.model.User;
import com.learning.blogplatformapi.service.UserService;
import com.learning.blogplatformapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){

        Optional<User> userOptional = userService.findByUsername(loginRequest.username());

        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(passwordEncoder.matches(loginRequest.password(), user.getPasswordHash())){
                String token = jwtUtil.generateToken(user);
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }

    public record LoginRequest(String username, String password) {}
}
