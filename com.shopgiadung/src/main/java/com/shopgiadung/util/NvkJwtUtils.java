package com.shopgiadung.util;

import com.shopgiadung.entity.NvkUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class NvkJwtUtils {

    // Thực tế: đưa vào env var (e.g. SPRING_JWT_SECRET) và đọc bằng @Value hoặc System.getenv
    private final String SECRET = "f4c9a7b1e6d0a5f8b3c2d4e7f1a0c8b9d2e1f0c3a4b5d6e7f8a9b0c1d2e3f4a5";
    private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 24 giờ

    private Key getSignKey() {
        try {
            // Nếu bạn lưu SECRET dưới dạng Base64, dùng decode Base64:
            // byte[] keyBytes = Decoders.BASE64.decode(SECRET);
            // return Keys.hmacShaKeyFor(keyBytes);

            // Nếu SECRET là plain text (như ở đây), dùng bytes UTF-8:
            byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception ex) {
            // Nếu muốn, ném RuntimeException để fail fast khi config sai
            throw new IllegalStateException("Invalid JWT SECRET configuration", ex);
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            // Trả về null hoặc ném tùy yêu cầu; ở đây trả null để caller xử lý
            return null;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Lấy userId an toàn (trả null nếu không có)
    public Long extractUserId(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Object raw = claims.get("userId");
            if (raw == null) return null;
            if (raw instanceof Number) {
                return ((Number) raw).longValue();
            } else {
                // nếu lưu dạng string
                return Long.parseLong(raw.toString());
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> {
            Object r = claims.get("role");
            return r == null ? null : r.toString();
        });
    }

    public Boolean isTokenExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        if (exp == null) return true; // nếu không lấy được expiration thì coi là expired
        return exp.before(new Date());
    }

    public String generateToken(NvkUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().name());
        return createToken(claims, user.getEmail());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) return false;
        final String username = extractUsername(token);
        if (username == null) return false;
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
