package com.giadungmart.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()  // Cho phép API không cần login
                        .anyRequest().permitAll()                // Tất cả các URL khác đều mở
                )
                .formLogin(login -> login.disable())  // Tắt form login
                .httpBasic(basic -> basic.disable()); // Tắt basic auth

        return http.build();
    }
}
