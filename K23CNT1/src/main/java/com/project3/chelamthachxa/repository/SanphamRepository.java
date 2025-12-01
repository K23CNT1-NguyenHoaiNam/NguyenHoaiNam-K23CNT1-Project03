package com.project3.chelamthachxa.repository;

import com.project3.chelamthachxa.entity.Sanpham;
import org.springframework.data.jpa.repository.JpaRepository;

// Lớp này dùng để thao tác với bảng Sản phẩm trong CSDL
public interface SanphamRepository extends JpaRepository<Sanpham, Long> {
    // Không cần thêm method nào ngoài CRUD cơ bản của JpaRepository
}