package com.project3.chelamthachxa.dto;

import com.project3.chelamthachxa.entity.Donhang;
import lombok.Data;
import java.util.List;

// Chi tiết sản phẩm trong đơn hàng
@Data
public class OrderItemRequest {
    private Long sanphamId;
    private Integer soLuong;
}
