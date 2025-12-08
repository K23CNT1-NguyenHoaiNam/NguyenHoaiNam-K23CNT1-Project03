package com.project3.chelamthachxa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "sanpham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sanpham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // THÊM @Column: Đảm bảo Hibernate ánh xạ đúng tên cột
    @Column(name = "tensanpham", nullable = false)
    private String tenSanPham;

    @Column(name = "mota", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "gia", nullable = false)
    private BigDecimal gia;

    // THÊM @Column: Đảm bảo Hibernate ánh xạ đúng tên cột
    @Column(name = "soluongton")
    private Integer soLuongTon;

    // Đảm bảo tên cột khớp với CSDL
    @Column(name = "image_url")
    private String imageUrl;
}