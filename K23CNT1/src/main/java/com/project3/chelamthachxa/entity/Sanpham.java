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

    @Column(nullable = false)
    private String tenSanPham;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    @Column(nullable = false)
    private BigDecimal gia;

    private Integer soLuongTon;

    // Đường dẫn ảnh sản phẩm
    private String imageUrl;
}