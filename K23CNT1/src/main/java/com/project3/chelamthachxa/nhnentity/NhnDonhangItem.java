package com.project3.chelamthachxa.nhnentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "nhn_donhang_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhnDonhangItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nhn_donhang_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nhn_donhang_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    private NhnDonhang nhnDonhang;

    @ManyToOne
    @JoinColumn(name = "nhn_sanpham_id", nullable = false)
    private NhnSanpham nhnSanpham;

    @Column(name = "nhn_soluong", nullable = false)
    private Integer soLuong;

    @Column(name = "nhn_giaban", nullable = false)
    private BigDecimal giaBan; // Giá tại thời điểm đặt hàng
}