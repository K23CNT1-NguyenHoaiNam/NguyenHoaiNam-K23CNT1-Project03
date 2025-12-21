package com.project3.chelamthachxa.nhnentity; // Đã chuẩn hóa package

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "nhn_donhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhnDonhang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nhn_donhang_id")
    private Long id;

    @Column(name = "nhn_ngaydathang")
    private LocalDateTime ngayDatHang = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "nhn_trangthai")
    private TrangThaiDonHang trangThai = TrangThaiDonHang.PENDING;

    @Column(name = "nhn_tongtien", nullable = false)
    private BigDecimal tongTien;

    // Thông tin người nhận (có thể khác với NhnUser)
    @Column(name = "nhn_tennguoinhan")
    private String tenNguoiNhan;
    @Column(name = "nhn_diachigiaohang")
    private String diaChiGiaoHang;
    @Column(name = "nhn_sdt")
    private String sdt;

    @ManyToOne
    @JoinColumn(name = "nhn_user_id", nullable = false)
    private NhnUser nhnUser; // Đơn hàng thuộc về NhnUser nào (Tên thuộc tính là nhnUser)

    // Quan hệ 1-N với NhnDonhangItem
    // Đảm bảo "mappedBy" trỏ đúng đến tên thuộc tính trong NhnDonhangItem.
    // Nếu bạn cũng đã đổi tên thuộc tính đó, hãy kiểm tra lại.
    // (Giữ nguyên "nhnDonhang" như code gốc của bạn đã sửa lỗi trước đó)
    @OneToMany(mappedBy = "nhnDonhang", cascade = CascadeType.ALL, orphanRemoval = true)
    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    private Set<NhnDonhangItem> nhnDonhangItems;

    public enum TrangThaiDonHang {
        PENDING, // Chờ xử lý
        PROCESSING, // Đang xử lý
        SHIPPED, // Đã giao hàng
        DELIVERED, // Đã nhận hàng
        CANCELLED // Đã hủy
    }
}