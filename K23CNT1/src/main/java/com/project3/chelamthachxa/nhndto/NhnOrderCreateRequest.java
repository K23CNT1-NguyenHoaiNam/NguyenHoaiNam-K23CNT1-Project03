package com.project3.chelamthachxa.nhndto;

import lombok.Data;
import java.util.List;

// Dùng để tạo mới đơn hàng
@Data
public class NhnOrderCreateRequest {
    private Long userId; // ID người dùng đặt hàng
    private String tenNguoiNhan;
    private String diaChiGiaoHang;
    private String sdt;
    // Đã đổi thành import từ file NhnOrderItemRequest.java mới
    private List<NhnOrderItemRequest> items; // Danh sách sản phẩm muốn mua
}