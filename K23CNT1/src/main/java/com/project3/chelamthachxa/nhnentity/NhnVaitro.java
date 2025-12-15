package com.project3.chelamthachxa.nhnentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nhn_vaitro") // ĐÃ SỬA TÊN BẢNG
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhnVaitro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nhn_vaitro_id") // ĐÃ SỬA TÊN CỘT ID
    private Long id;

    // Tên vai trò, ví dụ: ROLE_ADMIN, ROLE_USER
    @Column(name = "nhn_ten", nullable = false, unique = true) // ĐÃ SỬA TÊN CỘT
    private String ten;
}