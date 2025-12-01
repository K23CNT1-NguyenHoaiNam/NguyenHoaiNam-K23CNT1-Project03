package com.project3.chelamthachxa.dto;

import lombok.Data;

// Dùng để nhận dữ liệu từ form Đăng nhập
@Data
public class LoginRequest {
    private String username;
    private String password;
}