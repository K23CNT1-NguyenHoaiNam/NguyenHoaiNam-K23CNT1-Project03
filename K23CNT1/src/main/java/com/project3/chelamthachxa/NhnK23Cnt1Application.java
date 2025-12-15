package com.project3.chelamthachxa; // Đã sửa từ com.project3.nhn

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration; // Import BẮT BUỘC

// Loại trừ lớp tự động cấu hình UserDetailsService để Spring Security chỉ dùng CustomUserDetailsService của chúng ta
// và chỉ định rõ package quét nếu bạn có sub-package không chuẩn.
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class NhnK23Cnt1Application {

    public static void main(String[] args) {
        SpringApplication.run(NhnK23Cnt1Application.class, args);
    }
}