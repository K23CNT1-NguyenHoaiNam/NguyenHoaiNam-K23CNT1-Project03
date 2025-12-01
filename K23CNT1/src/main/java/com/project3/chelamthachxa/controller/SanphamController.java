package com.project3.chelamthachxa.controller;

import com.project3.chelamthachxa.entity.Sanpham;
import com.project3.chelamthachxa.service.SanphamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Dùng cho REST API
@RequestMapping("/api/products")
public class SanphamController {

    @Autowired
    private SanphamService sanphamService;

    // READ: Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<Sanpham>> getAllProducts() {
        List<Sanpham> products = sanphamService.findAll();
        return ResponseEntity.ok(products);
    }

    // READ: Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Sanpham> getProductById(@PathVariable Long id) {
        return sanphamService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE: Thêm mới sản phẩm (Chỉ ADMIN mới được truy cập)
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')") // Cần thêm @EnableMethodSecurity vào SecurityConfig
    // Hoặc dùng cấu hình URL trong SecurityConfig (đã làm)
    public ResponseEntity<Sanpham> createProduct(@RequestBody Sanpham sanpham) {
        Sanpham newProduct = sanphamService.save(sanpham);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // UPDATE: Cập nhật sản phẩm theo ID (Chỉ ADMIN mới được truy cập)
    @PutMapping("/{id}")
    public ResponseEntity<Sanpham> updateProduct(@PathVariable Long id, @RequestBody Sanpham sanphamDetails) {
        return sanphamService.findById(id)
                .map(existingProduct -> {
                    // Cập nhật các trường
                    existingProduct.setTenSanPham(sanphamDetails.getTenSanPham());
                    existingProduct.setMoTa(sanphamDetails.getMoTa());
                    existingProduct.setGia(sanphamDetails.getGia());
                    existingProduct.setSoLuongTon(sanphamDetails.getSoLuongTon());
                    existingProduct.setImageUrl(sanphamDetails.getImageUrl());

                    Sanpham updatedProduct = sanphamService.save(existingProduct);
                    return ResponseEntity.ok(updatedProduct);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Xóa sản phẩm theo ID (Chỉ ADMIN mới được truy cập)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        try {
            sanphamService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Mã 204: Xóa thành công, không trả về nội dung
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}