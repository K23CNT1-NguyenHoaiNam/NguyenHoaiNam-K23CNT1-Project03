package com.project3.chelamthachxa.nhnrepository; // Đã chuẩn hóa package

import com.project3.chelamthachxa.nhnentity.NhnDonhang; // Đã sửa import
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Lớp này dùng để thao tác với bảng Đơn hàng trong CSDL
public interface NhnDonhangRepository extends JpaRepository<NhnDonhang, Long> {

    /**
     * Tìm đơn hàng theo ID người dùng.
     * Spring Data JPA sẽ dịch phương thức này thành JPQL/SQL.
     * Tên phương thức: findByNhnUser_Id
     * - "NhnUser": Là tên thuộc tính trong Entity NhnDonhang (private NhnUser nhnUser;)
     * - "_Id": Yêu cầu truy cập ID (Primary Key) của đối tượng NhnUser.
     * @param userId ID của người dùng.
     * @return Danh sách đơn hàng của người dùng đó.
     */
    List<NhnDonhang> findByNhnUser_Id(Long userId); // ĐÃ SỬA LỖI TỪ findByUserId
}