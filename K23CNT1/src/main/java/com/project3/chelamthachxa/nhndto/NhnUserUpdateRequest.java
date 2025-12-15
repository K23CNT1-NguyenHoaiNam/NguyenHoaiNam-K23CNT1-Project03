package com.project3.chelamthachxa.nhndto;

import lombok.Data;

// Dùng để Admin cập nhật thông tin người dùng
@Data
public class NhnUserUpdateRequest {
    private String hoten;
    private String email;
    private String diachi;
    private String sdt;
}