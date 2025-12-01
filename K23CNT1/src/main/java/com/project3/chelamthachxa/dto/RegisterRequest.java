package com.project3.chelamthachxa.dto;

import lombok.Data;

// Dùng để nhận dữ liệu từ form Đăng ký
@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String hoten;
}