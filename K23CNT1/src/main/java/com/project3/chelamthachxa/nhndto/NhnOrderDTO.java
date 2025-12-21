package com.project3.chelamthachxa.nhndto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NhnOrderDTO {
    private Long id;
    private Long userId; // Chỉ lấy ID
    private String username; // Thêm username để hiển thị
    private String tenNguoiNhan;
    private String sdt;
    private String diaChiGiaoHang;
    private LocalDateTime ngayDatHang;
    private BigDecimal tongTien;
    private String trangThai;
    private List<NhnOrderItemDTO> items;

    @Data
    public static class NhnOrderItemDTO {
        private Long productId;
        private String productName;
        private int quantity;
        private BigDecimal price;
        private String imageUrl;
    }
}
