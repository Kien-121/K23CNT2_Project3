package com.shopgiadung.config;

import com.shopgiadung.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Thêm import này
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    // Spring Security sẽ tự động tìm Bean này để xác thực
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean AuthenticationManager để xử lý đăng nhập
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Cấu hình CORS để cho phép Frontend gọi API
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080", "null", "*")); // Cho phép localhost và file://
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Kích hoạt CORS

                // Cấu hình UserDetailsService và PasswordEncoder trực tiếp trên HttpSecurity


                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLIC ACCESS (Cho phép truy cập không cần đăng nhập)
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/auth.html",
                                "/news.html",
                                "/news_detail.html", // Cho phép xem chi tiết tin tức
                                "/reviews.html",
                                "/contact.html", // Cho phép xem trang liên hệ
                                "/promotions.html",
                                "/my_orders.html",
                                "/admin.html",
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        // API Public (GET only cho reviews và news)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/categories/**").permitAll()

                        // SỬA LỖI ĐÁNH GIÁ: Chỉ cho phép GET (xem) là public, còn POST (thêm) phải đăng nhập
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/reviews/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/news/**").permitAll()

                        // Cho phép lấy danh sách khuyến mãi công khai (chỉ GET)
                        .requestMatchers(HttpMethod.GET, "/api/promotions/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/my_orders/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/promotions/**").permitAll()

                        // 2. ADMIN ACCESS (Chỉ Admin mới được gọi API quản trị - các method POST/PUT/DELETE)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 3. AUTHENTICATED ACCESS (Các API khác yêu cầu đăng nhập)
                        .requestMatchers("/api/orders/**").authenticated()

                        .anyRequest().authenticated()
                )

                // Quản lý session không trạng thái (Stateless) cho JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Tắt form login mặc định
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())

                // Thêm Filter kiểm tra JWT trước
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}