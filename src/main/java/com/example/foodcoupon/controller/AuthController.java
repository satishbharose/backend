package com.example.foodcoupon.controller;

import com.example.foodcoupon.model.User;
import com.example.foodcoupon.repository.UserRepository;
import com.example.foodcoupon.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User u = userRepository.findByUsername(username)
                    .orElseThrow(NoSuchElementException::new);
            String token = jwtUtil.generateToken(u.getUsername(), u.getRole());
            HashMap<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", u.getRole());
            response.put("username", u.getUsername());
            response.put("userId", u.getId());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("error", "Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
    }

    /*@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error","Username exists"));
        }
        User u = new User();
        u.setName(body.getOrDefault("name", username));
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(body.get("password")));
        u.setRole(body.getOrDefault("role","USER"));
        userRepository.save(u);
        return ResponseEntity.ok(u);
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (userRepository.findByUsername(username).isPresent()) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("error", "Username exists");
            return ResponseEntity.badRequest().body(response);
        }
        User u = new User();
        u.setName(body.getOrDefault("name", username));
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(body.get("password")));
        u.setRole(body.getOrDefault("role", "USER"));
        userRepository.save(u);
        return ResponseEntity.ok(u);
    }
}
