package com.project3.chelamthachxa.dto;

import lombok.Data;
import java.util.List;

// Dùng để tạo mới đơn hàng
@Data
public class OrderCreateRequest {
    private Long userId; // ID người dùng đặt hàng
    private String tenNguoiNhan;
    private String diaChiGiaoHang;
    private String sdt;
    // Đã đổi thành import từ file OrderItemRequest.java mới
    private List<OrderItemRequest> items; // Danh sách sản phẩm muốn mua
}