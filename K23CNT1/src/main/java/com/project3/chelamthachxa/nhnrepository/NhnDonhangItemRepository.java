package com.project3.chelamthachxa.nhnrepository;

import com.project3.chelamthachxa.nhnentity.NhnDonhangItem;
import org.springframework.data.jpa.repository.JpaRepository;

// Lớp này dùng để thao tác với bảng Chi tiết Đơn hàng trong CSDL
public interface NhnDonhangItemRepository extends JpaRepository<NhnDonhangItem, Long> {
    // Không cần thêm phương thức đặc biệt, JpaRepository đã cung cấp CRUD
}