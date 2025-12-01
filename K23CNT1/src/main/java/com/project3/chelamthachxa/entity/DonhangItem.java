package com.project3.chelamthachxa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "donhang_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonhangItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donhang_id", nullable = false)
    private Donhang donhang;

    @ManyToOne
    @JoinColumn(name = "sanpham_id", nullable = false)
    private Sanpham sanpham;

    @Column(nullable = false)
    private Integer soLuong;

    @Column(nullable = false)
    private BigDecimal giaBan; // Giá tại thời điểm đặt hàng
}