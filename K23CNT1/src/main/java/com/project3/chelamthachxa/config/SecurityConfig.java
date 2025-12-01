package com.project3.chelamthachxa.config;

import com.project3.chelamthachxa.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// IMPORTS THIẾU BẮT BUỘC CHO CÁC BEAN BẢO MẬT
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    // SỬ DỤNG CONSTRUCTOR INJECTION
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 1. Bean để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Bean DaoAuthenticationProvider: Liên kết UserDetailsService và PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 3. Bean AuthenticationManager: Dùng để quản lý quá trình xác thực
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Spring Security sẽ tự động tìm kiếm DaoAuthenticationProvider đã định nghĩa ở trên
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // THÊM CẤU HÌNH AUTHENTICATION PROVIDER TRỰC TIẾP VÀO HTTPSECURITY
                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(authorize -> authorize
                        // 1. Cho phép truy cập vào các tài nguyên tĩnh chuẩn
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        // 2. CHO PHÉP TRUY CẬP ĐƯỜNG DẪN IMAGES MỚI
                        .requestMatchers("/images/**").permitAll()

                        // 3. Cho phép truy cập công khai vào các đường dẫn liên quan đến Đăng ký/Đăng nhập và trang chủ.
                        .requestMatchers(
                                "/auth/**",
                                "/",
                                "/products/**"
                        ).permitAll()

                        // 4. Chỉ cho phép ADMIN truy cập các chức năng quản lý (CRUD)
                        .requestMatchers(
                                "/api/admin/**",
                                "/admin/**"
                        ).hasRole("ADMIN")

                        // 5. Yêu cầu xác thực cho tất cả các request khác
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}