package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhndto.NhnOrderCreateRequest;
import com.project3.chelamthachxa.nhnentity.NhnDonhang;
import com.project3.chelamthachxa.nhnservice.NhnDonhangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders") // Dùng cho người dùng đặt hàng và xem đơn
public class NhnDonhangController {

    @Autowired
    private NhnDonhangService nhnDonhangService;

    // CREATE: Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<NhnDonhang> createOrder(@RequestBody NhnOrderCreateRequest request) {
        try {
            NhnDonhang nhnDonhang = nhnDonhangService.createOrder(request);
            return new ResponseEntity<>(nhnDonhang, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // READ: Xem đơn hàng của một người dùng (Dành cho người dùng xem lịch sử mua hàng)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NhnDonhang>> getOrdersByUserId(@PathVariable Long userId) {
        List<NhnDonhang> orders = nhnDonhangService.findOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // READ: Xem chi tiết đơn hàng
    @GetMapping("/{id}")
    public ResponseEntity<com.project3.chelamthachxa.nhndto.NhnOrderDTO> getOrderById(@PathVariable Long id) {
        return nhnDonhangService.findOrderDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ADMIN: Lấy tất cả đơn hàng (Chỉ Admin)
    @GetMapping("/admin/all")
    public ResponseEntity<List<com.project3.chelamthachxa.nhndto.NhnOrderDTO>> getAllOrders() { // Full path for DTO to avoid import ambiguity if any
        return ResponseEntity.ok(nhnDonhangService.findAllOrderDTOs());
    }

    // ADMIN: Cập nhật trạng thái đơn hàng (Chỉ Admin)
    @PutMapping("/admin/{orderId}/status")
    public ResponseEntity<com.project3.chelamthachxa.nhndto.NhnOrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam NhnDonhang.TrangThaiDonHang newStatus) {
        try {
            com.project3.chelamthachxa.nhndto.NhnOrderDTO updatedOrder = nhnDonhangService.updateOrderStatusDTO(orderId, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}