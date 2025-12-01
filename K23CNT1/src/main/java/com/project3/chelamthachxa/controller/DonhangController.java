package com.project3.chelamthachxa.controller;

import com.project3.chelamthachxa.dto.OrderCreateRequest;
import com.project3.chelamthachxa.entity.Donhang;
import com.project3.chelamthachxa.service.DonhangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders") // Dùng cho người dùng đặt hàng và xem đơn
public class DonhangController {

    @Autowired
    private DonhangService donhangService;

    // CREATE: Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<Donhang> createOrder(@RequestBody OrderCreateRequest request) {
        try {
            Donhang donhang = donhangService.createOrder(request);
            return new ResponseEntity<>(donhang, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // READ: Xem đơn hàng của một người dùng (Dành cho người dùng xem lịch sử mua hàng)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Donhang>> getOrdersByUserId(@PathVariable Long userId) {
        List<Donhang> orders = donhangService.findOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // READ: Xem chi tiết đơn hàng
    @GetMapping("/{id}")
    public ResponseEntity<Donhang> getOrderById(@PathVariable Long id) {
        return donhangService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ADMIN: Lấy tất cả đơn hàng (Chỉ Admin)
    @GetMapping("/admin/all")
    public ResponseEntity<List<Donhang>> getAllOrders() {
        return ResponseEntity.ok(donhangService.findAllOrders());
    }

    // ADMIN: Cập nhật trạng thái đơn hàng (Chỉ Admin)
    @PutMapping("/admin/{orderId}/status")
    public ResponseEntity<Donhang> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Donhang.TrangThaiDonHang newStatus) {
        try {
            Donhang updatedOrder = donhangService.updateOrderStatus(orderId, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}