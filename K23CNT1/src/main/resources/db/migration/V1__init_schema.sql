DROP DATABASE IF EXISTS project03db;

-- Tên cơ sở dữ liệu mới
CREATE DATABASE IF NOT EXISTS project03db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Chọn cơ sở dữ liệu vừa tạo
USE project03db;

-- ---------------------------------------------------------------------
-- 1. BẢNG VAI TRÒ (nhn_vaitro)
-- ---------------------------------------------------------------------
CREATE TABLE nhn_vaitro (
    nhn_vaitro_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nhn_ten VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 2. BẢNG NGƯỜI DÙNG (nhn_nguoidung)
-- ---------------------------------------------------------------------
CREATE TABLE nhn_nguoidung (
    nhn_nguoidung_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nhn_username VARCHAR(100) NOT NULL UNIQUE,
    nhn_email VARCHAR(150) NOT NULL UNIQUE,
    nhn_password VARCHAR(255) NOT NULL,
    nhn_hoten VARCHAR(255),
    nhn_diachi VARCHAR(255),
    nhn_sdt VARCHAR(20)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 3. BẢNG QUAN HỆ NHIỀU-NHIỀU (nhn_user_vaitro)
-- ---------------------------------------------------------------------
CREATE TABLE nhn_user_vaitro (
    nhn_user_id BIGINT NOT NULL,
    nhn_vaitro_id BIGINT NOT NULL,
    PRIMARY KEY (nhn_user_id, nhn_vaitro_id),
    FOREIGN KEY (nhn_user_id) REFERENCES nhn_nguoidung(nhn_nguoidung_id) ON DELETE CASCADE,
    FOREIGN KEY (nhn_vaitro_id) REFERENCES nhn_vaitro(nhn_vaitro_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 4. BẢNG SẢN PHẨM (nhn_sanpham)
-- ---------------------------------------------------------------------
CREATE TABLE nhn_sanpham (
    nhn_sanpham_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nhn_tensanpham VARCHAR(255) NOT NULL,
    nhn_mota TEXT,
    nhn_gia DECIMAL(10, 2) NOT NULL,
    nhn_soluongton INT,
    nhn_image_url VARCHAR(255)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 5. BẢNG ĐƠN HÀNG (nhn_donhang)
-- ---------------------------------------------------------------------
CREATE TABLE nhn_donhang (
    nhn_donhang_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nhn_user_id BIGINT NOT NULL,
    nhn_ngaydathang DATETIME NOT NULL,
    nhn_trangthai ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL,
    nhn_tongtien DECIMAL(10, 2) NOT NULL,
    nhn_tennguoinhan VARCHAR(255),
    nhn_diachigiaohang VARCHAR(255),
    nhn_sdt VARCHAR(20),
    FOREIGN KEY (nhn_user_id) REFERENCES nhn_nguoidung(nhn_nguoidung_id)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 6. BẢNG CHI TIẾT ĐƠN HÀNG (nhn_donhang_item)
-- ---------------------------------------------------------------------
CREATE TABLE nhn_donhang_item (
    nhn_donhang_item_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nhn_donhang_id BIGINT NOT NULL,
    nhn_sanpham_id BIGINT NOT NULL,
    nhn_soluong INT NOT NULL,
    nhn_giaban DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (nhn_donhang_id) REFERENCES nhn_donhang(nhn_donhang_id) ON DELETE CASCADE,
    FOREIGN KEY (nhn_sanpham_id) REFERENCES nhn_sanpham(nhn_sanpham_id)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- DỮ LIỆU MẪU (SEED DATA)
-- ---------------------------------------------------------------------

-- Thêm các vai trò cơ bản (ROLE_USER, ROLE_ADMIN)
INSERT INTO nhn_vaitro (nhn_ten) VALUES ('ROLE_USER');
INSERT INTO nhn_vaitro (nhn_ten) VALUES ('ROLE_ADMIN');

-- Thêm sản phẩm mẫu
INSERT INTO nhn_sanpham (nhn_tensanpham, nhn_mota, nhn_gia, nhn_soluongton, nhn_image_url) VALUES
('hộp chè lam (thường) 300g', 'thơm ngon mềm dẻo', 25000.00, 500, '/images/product_01.jpg'),
('hộp chè lam quai sách (thường) 1kg', 'thơm ngon mềm dẻo', 100000.00, 500, '/images/product_02.jpg'),
('hộp chè lam (thường) 500g', 'thơm ngon mềm dẻo, thêm hương vani', 100000.00, 705, '/images/product_03.jpg'),
('hộp chè lam (mè đen) 500g', 'thơm ngon mềm dẻo, thêm hương mè đen', 100000.00, 750, '/images/product_04.jpg'),
('hộp chè lam (mè trắng) 500g', 'thơm ngon mềm dẻo, thêm hương mè trắng', 100000.00, 705, '/images/product_05.jpg'),
('hộp chè lam quai sách (mè đen) 1kg', 'thơm ngon mềm dẻo, thêm hương mè đen', 120000.00, 705, '/images/product_06.jpg');

INSERT IGNORE INTO nhn_nguoidung (nhn_username, nhn_email, nhn_password, nhn_hoten, nhn_diachi, nhn_sdt) VALUES
('user_test', 'user@test.com', '$2a$10$eE0h2.T.13yT79F4M/WbXuG5vW6vP0J1e9O2b4C6yD7E8F9G0H1I', 'Nguyễn Văn A', '456 Phố Người Dùng, HCM', '0901234567');

INSERT IGNORE INTO nhn_user_vaitro (nhn_user_id, nhn_vaitro_id)
SELECT n.nhn_nguoidung_id, v.nhn_vaitro_id
FROM nhn_nguoidung n, nhn_vaitro v
WHERE n.nhn_username = 'user_test' AND v.nhn_ten = 'ROLE_USER';

SET @user_id = (SELECT nhn_nguoidung_id FROM nhn_nguoidung WHERE nhn_username = 'user_test');
SET @sanpham1_id = (SELECT nhn_sanpham_id FROM nhn_sanpham WHERE nhn_tensanpham LIKE 'hộp chè lam (thường) 300g');
SET @sanpham2_id = (SELECT nhn_sanpham_id FROM nhn_sanpham WHERE nhn_tensanpham LIKE 'hộp chè lam quai sách (thường) 1kg');
SET @sanpham4_id = (SELECT nhn_sanpham_id FROM nhn_sanpham WHERE nhn_tensanpham LIKE 'hộp chè lam (mè đen) 500g');

INSERT INTO nhn_donhang (nhn_user_id, nhn_ngaydathang, nhn_trangthai, nhn_tongtien, nhn_tennguoinhan, nhn_diachigiaohang, nhn_sdt) VALUES
(@user_id, NOW() - INTERVAL 2 DAY, 'PENDING', 125000.00, 'Nguyễn Văn A', '456 Phố Người Dùng, HCM', '0901234567');
SET @donhang1_id = LAST_INSERT_ID();

INSERT INTO nhn_donhang_item (nhn_donhang_id, nhn_sanpham_id, nhn_soluong, nhn_giaban) VALUES
(@donhang1_id, @sanpham1_id, 2, 25000.00),
(@donhang1_id, @sanpham2_id, 1, 100000.00);

UPDATE nhn_donhang SET nhn_tongtien = 150000.00 WHERE nhn_donhang_id = @donhang1_id;

INSERT INTO nhn_donhang (nhn_user_id, nhn_ngaydathang, nhn_trangthai, nhn_tongtien, nhn_tennguoinhan, nhn_diachigiaohang, nhn_sdt) VALUES
(@user_id, NOW() - INTERVAL 10 DAY, 'DELIVERED', 45000.00, 'Nguyễn Văn A', '456 Phố Người Dùng, HCM', '0901234567');
SET @donhang2_id = LAST_INSERT_ID();

INSERT INTO nhn_donhang_item (nhn_donhang_id, nhn_sanpham_id, nhn_soluong, nhn_giaban) VALUES
(@donhang2_id, @sanpham4_id, 1, 45000.00);

INSERT INTO nhn_donhang (nhn_user_id, nhn_ngaydathang, nhn_trangthai, nhn_tongtien, nhn_tennguoinhan, nhn_diachigiaohang, nhn_sdt) VALUES
(@admin_id, NOW() - INTERVAL 1 DAY, 'PROCESSING', 100000.00, 'Quản trị viên', '123 Đường Admin, Hà Nội', '0987654321');
SET @donhang3_id = LAST_INSERT_ID();

INSERT INTO nhn_donhang_item (nhn_donhang_id, nhn_sanpham_id, nhn_soluong, nhn_giaban) VALUES
(@donhang3_id, @sanpham2_id, 1, 100000.00);

INSERT INTO nhn_donhang (nhn_user_id, nhn_ngaydathang, nhn_trangthai, nhn_tongtien, nhn_tennguoinhan, nhn_diachigiaohang, nhn_sdt) VALUES
(@user_id, NOW(), 'PENDING', 25000.00, 'Nguyễn Văn A', '456 Phố Người Dùng, HCM', '0901234567');
SET @donhang4_id = LAST_INSERT_ID();

INSERT INTO nhn_donhang_item (nhn_donhang_id, nhn_sanpham_id, nhn_soluong, nhn_giaban) VALUES
(@donhang4_id, @sanpham1_id, 1, 25000.00);
