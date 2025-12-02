package com.giadungmart.controller;

import com.giadungmart.dto.AuthRequest;
import com.giadungmart.dto.AuthResponse;
import com.giadungmart.entity.User;
import com.giadungmart.security.JwtUtil;
import com.giadungmart.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UserService userService, JwtUtil jwtUtil, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        return userService.save(user);
    }

    // LOGIN
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {

        User user = userService.findByUsername(req.getUsername());

        if (user == null || !encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new AuthResponse(token);
    }
}
