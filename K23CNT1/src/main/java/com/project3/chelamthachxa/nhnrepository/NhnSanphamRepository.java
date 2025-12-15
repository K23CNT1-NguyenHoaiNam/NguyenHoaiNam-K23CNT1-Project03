package com.project3.chelamthachxa.nhnrepository;

import com.project3.chelamthachxa.nhnentity.NhnSanpham;
import org.springframework.data.jpa.repository.JpaRepository;

// Lớp này dùng để thao tác với bảng Sản phẩm trong CSDL
public interface NhnSanphamRepository extends JpaRepository<NhnSanpham, Long> {
    // Không cần thêm method nào ngoài CRUD cơ bản của JpaRepository
}