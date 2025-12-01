package com.project3.chelamthachxa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "donhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donhang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime ngayDatHang = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TrangThaiDonHang trangThai = TrangThaiDonHang.PENDING;

    @Column(nullable = false)
    private BigDecimal tongTien;

    // Thông tin người nhận (có thể khác với User)
    private String tenNguoiNhan;
    private String diaChiGiaoHang;
    private String sdt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Đơn hàng thuộc về User nào

    // Quan hệ 1-N với DonhangItem
    @OneToMany(mappedBy = "donhang", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DonhangItem> donhangItems;

    public enum TrangThaiDonHang {
        PENDING, // Chờ xử lý
        PROCESSING, // Đang xử lý
        SHIPPED, // Đã giao hàng
        DELIVERED, // Đã nhận hàng
        CANCELLED // Đã hủy
    }
}