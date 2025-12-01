package com.project3.chelamthachxa.repository;

import com.project3.chelamthachxa.entity.Donhang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Lớp này dùng để thao tác với bảng Đơn hàng trong CSDL
public interface DonhangRepository extends JpaRepository<Donhang, Long> {
    // Tìm đơn hàng theo ID người dùng
    List<Donhang> findByUserId(Long userId);
}