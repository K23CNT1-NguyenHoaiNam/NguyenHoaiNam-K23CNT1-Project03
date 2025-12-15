package com.project3.chelamthachxa.nhndto;

import lombok.Data;

// Dùng để nhận dữ liệu từ form Đăng nhập
@Data
public class NhnLoginRequest {
    private String username;
    private String password;
}