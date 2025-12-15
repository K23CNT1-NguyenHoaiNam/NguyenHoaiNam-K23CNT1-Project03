package com.project3.chelamthachxa.nhnentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "nhn_sanpham") // ĐÃ SỬA TÊN BẢNG
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhnSanpham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nhn_sanpham_id") // ĐÃ SỬA TÊN CỘT ID
    private Long id;

    @Column(name = "nhn_tensanpham", nullable = false) // ĐÃ SỬA TÊN CỘT
    private String tenSanPham;

    @Column(name = "nhn_mota", columnDefinition = "TEXT") // ĐÃ SỬA TÊN CỘT
    private String moTa;

    @Column(name = "nhn_gia", nullable = false) // ĐÃ SỬA TÊN CỘT
    private BigDecimal gia;

    @Column(name = "nhn_soluongton") // ĐÃ SỬA TÊN CỘT
    private Integer soLuongTon;

    @Column(name = "nhn_image_url") // ĐÃ SỬA TÊN CỘT
    private String imageUrl;
}