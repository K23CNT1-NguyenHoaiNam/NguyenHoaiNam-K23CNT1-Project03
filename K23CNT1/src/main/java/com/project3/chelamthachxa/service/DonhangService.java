package com.project3.chelamthachxa.service;

import com.project3.chelamthachxa.dto.OrderCreateRequest;
import com.project3.chelamthachxa.dto.OrderItemRequest;
import com.project3.chelamthachxa.entity.*;
import com.project3.chelamthachxa.repository.DonhangItemRepository;
import com.project3.chelamthachxa.repository.DonhangRepository;
import com.project3.chelamthachxa.repository.SanphamRepository;
import com.project3.chelamthachxa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DonhangService {

    @Autowired
    private DonhangRepository donhangRepository;
    @Autowired
    private DonhangItemRepository donhangItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SanphamRepository sanphamRepository;

    // Lấy tất cả đơn hàng (Admin)
    public List<Donhang> findAllOrders() {
        return donhangRepository.findAll();
    }

    // Lấy đơn hàng theo ID
    public Optional<Donhang> findOrderById(Long id) {
        return donhangRepository.findById(id);
    }

    // Lấy đơn hàng theo User ID
    public List<Donhang> findOrdersByUserId(Long userId) {
        return donhangRepository.findByUserId(userId);
    }

    // CREATE: Tạo đơn hàng mới
    @Transactional
    public Donhang createOrder(OrderCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));

        Donhang donhang = new Donhang();
        donhang.setUser(user);
        donhang.setTenNguoiNhan(request.getTenNguoiNhan());
        donhang.setDiaChiGiaoHang(request.getDiaChiGiaoHang());
        donhang.setSdt(request.getSdt());
        donhang.setTrangThai(Donhang.TrangThaiDonHang.PENDING);

        Set<DonhangItem> items = new HashSet<>();
        BigDecimal tongTien = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Sanpham sanpham = sanphamRepository.findById(itemRequest.getSanphamId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + itemRequest.getSanphamId()));

            if (sanpham.getSoLuongTon() < itemRequest.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + sanpham.getTenSanPham() + " không đủ số lượng.");
            }

            DonhangItem item = new DonhangItem();
            item.setDonhang(donhang);
            item.setSanpham(sanpham);
            item.setSoLuong(itemRequest.getSoLuong());
            item.setGiaBan(sanpham.getGia());

            BigDecimal subtotal = sanpham.getGia().multiply(BigDecimal.valueOf(itemRequest.getSoLuong()));
            tongTien = tongTien.add(subtotal);

            items.add(item);

            // Cập nhật số lượng tồn kho
            sanpham.setSoLuongTon(sanpham.getSoLuongTon() - itemRequest.getSoLuong());
            sanphamRepository.save(sanpham);
        }

        donhang.setDonhangItems(items);
        donhang.setTongTien(tongTien);

        return donhangRepository.save(donhang);
    }

    // UPDATE: Cập nhật trạng thái đơn hàng (Admin)
    @Transactional
    public Donhang updateOrderStatus(Long orderId, Donhang.TrangThaiDonHang newStatus) {
        return donhangRepository.findById(orderId)
                .map(donhang -> {
                    donhang.setTrangThai(newStatus);
                    return donhangRepository.save(donhang);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đơn hàng với ID: " + orderId));
    }
}