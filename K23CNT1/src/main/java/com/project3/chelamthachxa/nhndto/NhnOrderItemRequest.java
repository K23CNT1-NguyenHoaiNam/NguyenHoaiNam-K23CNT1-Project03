package com.project3.chelamthachxa.nhndto;

import lombok.Data;

// Chi tiết sản phẩm trong đơn hàng
@Data
public class NhnOrderItemRequest {
    private Long sanphamId;
    private Integer soLuong;
}
