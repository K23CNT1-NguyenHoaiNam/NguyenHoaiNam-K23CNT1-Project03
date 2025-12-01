package com.project3.chelamthachxa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration; // Import BẮT BUỘC

// Loại trừ lớp tự động cấu hình UserDetailsService để Spring Security chỉ dùng CustomUserDetailsService của chúng ta
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class K23Cnt1Application {

    public static void main(String[] args) {
        SpringApplication.run(K23Cnt1Application.class, args);
    }

}