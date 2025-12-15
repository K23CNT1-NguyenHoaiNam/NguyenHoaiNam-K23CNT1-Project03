package com.project3.chelamthachxa.nhnconfig;

import com.project3.chelamthachxa.nhnservice.NhnCustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class NhnSecurityConfig {

    private final NhnCustomUserDetailsService userDetailsService;

    public NhnSecurityConfig(NhnCustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(authorize -> authorize
                        // 1. Cho phép truy cập vào các tài nguyên tĩnh chuẩn (CSS, JS, images,...)
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        // 2. CHO PHÉP TRUY CẬP TẤT CẢ CÁC TÀI NGUYÊN TĨNH
                        .requestMatchers("/css/**", "/images/**").permitAll()

                        // 3. Phân quyền rõ ràng cho các URL Auth Controller và trang chủ.
                        .requestMatchers(
                                "/auth/**",           // Bao gồm /auth/login, /auth/register, và POST actions
                                "/",                  // Trang chủ
                                "/nhnproducts/**"        // Danh sách sản phẩm
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
                        // Trỏ đến URL Controller chính xác
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/nhnindex", true)
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