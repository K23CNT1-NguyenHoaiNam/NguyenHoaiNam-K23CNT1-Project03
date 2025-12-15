package com.project3.chelamthachxa.nhndto;

import lombok.Data;

// Dùng để nhận dữ liệu từ form Đăng ký
@Data
public class NhnRegisterRequest {
    private String username;
    private String email;
    private String password;
    private String hoten;
}