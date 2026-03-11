package com.example.Task.Management.API.controller;


import com.example.Task.Management.API.dto.AuthResponse;
import com.example.Task.Management.API.model.User;
import com.example.Task.Management.API.service.UserService;
import com.example.Task.Management.API.util.JwtUtil;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Builder
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final  JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        log.info("Received request to register user: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      return   ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        log.info("Received request to login user: {}", user.getUsername());
        AuthResponse authResponse = userService.authenticateUser(user.getUsername(), user.getPassword());
        log.info("Authentication successful for user: {}", user.getUsername());
        return ResponseEntity.ok(authResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<?> adminAccess() {
        log.info("Received request to get all users by admin");
        return ResponseEntity.ok(userService.getAllTasksForAdmin());
    }
}
