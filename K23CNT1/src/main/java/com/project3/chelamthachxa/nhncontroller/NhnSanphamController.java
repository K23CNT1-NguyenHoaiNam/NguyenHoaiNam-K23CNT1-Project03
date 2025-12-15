package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhnentity.NhnSanpham;
import com.project3.chelamthachxa.nhnservice.NhnSanphamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Dùng cho REST API
@RequestMapping("/api/products")
public class NhnSanphamController {

    @Autowired
    private NhnSanphamService nhnSanphamService;

    // READ: Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<NhnSanpham>> getAllProducts() {
        List<NhnSanpham> products = nhnSanphamService.findAll();
        return ResponseEntity.ok(products);
    }

    // READ: Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NhnSanpham> getProductById(@PathVariable Long id) {
        return nhnSanphamService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE: Thêm mới sản phẩm (Chỉ ADMIN mới được truy cập)
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')") // Cần thêm @EnableMethodSecurity vào SecurityConfig
    // Hoặc dùng cấu hình URL trong SecurityConfig (đã làm)
    public ResponseEntity<NhnSanpham> createProduct(@RequestBody NhnSanpham nhnSanpham) {
        NhnSanpham newProduct = nhnSanphamService.save(nhnSanpham);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // UPDATE: Cập nhật sản phẩm theo ID (Chỉ ADMIN mới được truy cập)
    @PutMapping("/{id}")
    public ResponseEntity<NhnSanpham> updateProduct(@PathVariable Long id, @RequestBody NhnSanpham nhnSanphamDetails) {
        return nhnSanphamService.findById(id)
                .map(existingProduct -> {
                    // Cập nhật các trường
                    existingProduct.setTenSanPham(nhnSanphamDetails.getTenSanPham());
                    existingProduct.setMoTa(nhnSanphamDetails.getMoTa());
                    existingProduct.setGia(nhnSanphamDetails.getGia());
                    existingProduct.setSoLuongTon(nhnSanphamDetails.getSoLuongTon());
                    existingProduct.setImageUrl(nhnSanphamDetails.getImageUrl());

                    NhnSanpham updatedProduct = nhnSanphamService.save(existingProduct);
                    return ResponseEntity.ok(updatedProduct);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Xóa sản phẩm theo ID (Chỉ ADMIN mới được truy cập)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        try {
            nhnSanphamService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Mã 204: Xóa thành công, không trả về nội dung
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}