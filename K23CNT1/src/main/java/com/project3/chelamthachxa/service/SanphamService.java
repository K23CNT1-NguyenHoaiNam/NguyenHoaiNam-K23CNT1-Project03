package com.project3.chelamthachxa.service;

import com.project3.chelamthachxa.entity.Sanpham;
import com.project3.chelamthachxa.repository.SanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanphamService {

    @Autowired
    private SanphamRepository sanphamRepository;

    // Lấy tất cả sản phẩm
    public List<Sanpham> findAll() {
        return sanphamRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<Sanpham> findById(Long id) {
        return sanphamRepository.findById(id);
    }

    // Thêm mới/Cập nhật sản phẩm
    public Sanpham save(Sanpham sanpham) {
        return sanphamRepository.save(sanpham);
    }

    // Xóa sản phẩm
    public void deleteById(Long id) {
        if (sanphamRepository.existsById(id)) {
            sanphamRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy Sản phẩm với ID: " + id);
        }
    }
}