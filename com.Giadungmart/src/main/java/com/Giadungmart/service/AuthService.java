package com.giadungmart.service;

import com.giadungmart.dto.AuthRequest;
import com.giadungmart.dto.AuthResponse;
import com.giadungmart.entity.User;
import com.giadungmart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse register(AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole("USER");

        userRepo.save(user);

        String token = jwtService.generateToken(user);
        String refresh = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(token, refresh, user.getUsername(), user.getRole());
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user);
        String refresh = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(token, refresh, user.getUsername(), user.getRole());
    }
}
