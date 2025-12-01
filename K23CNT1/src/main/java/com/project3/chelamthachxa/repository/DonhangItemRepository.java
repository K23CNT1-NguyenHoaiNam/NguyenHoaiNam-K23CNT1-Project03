package com.project3.chelamthachxa.repository;

import com.project3.chelamthachxa.entity.DonhangItem;
import org.springframework.data.jpa.repository.JpaRepository;

// Lớp này dùng để thao tác với bảng Chi tiết Đơn hàng trong CSDL
public interface DonhangItemRepository extends JpaRepository<DonhangItem, Long> {
    // Không cần thêm phương thức đặc biệt, JpaRepository đã cung cấp CRUD
}