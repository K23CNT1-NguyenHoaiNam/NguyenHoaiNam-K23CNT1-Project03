package com.project3.chelamthachxa.nhnservice; // Hoàn nguyên về tên package gốc

import com.project3.chelamthachxa.nhndto.NhnOrderCreateRequest;
import com.project3.chelamthachxa.nhndto.NhnOrderItemRequest;
import com.project3.chelamthachxa.nhnentity.*;
import com.project3.chelamthachxa.nhnrepository.NhnDonhangItemRepository;
import com.project3.chelamthachxa.nhnrepository.NhnDonhangRepository;
import com.project3.chelamthachxa.nhnrepository.NhnSanphamRepository;
import com.project3.chelamthachxa.nhnrepository.NhnUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NhnDonhangService {

    @Autowired
    private NhnDonhangRepository nhnDonhangRepository;
    @Autowired
    private NhnDonhangItemRepository nhnDonhangItemRepository;
    @Autowired
    private NhnUserRepository nhnUserRepository;
    @Autowired
    private NhnSanphamRepository nhnSanphamRepository;

    // Lấy tất cả đơn hàng (Admin)
    public List<NhnDonhang> findAllOrders() {
        return nhnDonhangRepository.findAll();
    }

    // ADMIN: Get All Orders as DTOs
    @Transactional // Quan trọng để giữ session mở khi truy cập lazy collections
    public List<com.project3.chelamthachxa.nhndto.NhnOrderDTO> findAllOrderDTOs() {
        List<NhnDonhang> orders = nhnDonhangRepository.findAll();
        return orders.stream().map(this::convertToDTO).collect(java.util.stream.Collectors.toList());
    }

    private com.project3.chelamthachxa.nhndto.NhnOrderDTO convertToDTO(NhnDonhang order) {
        com.project3.chelamthachxa.nhndto.NhnOrderDTO dto = new com.project3.chelamthachxa.nhndto.NhnOrderDTO();
        dto.setId(order.getId());
        dto.setNgayDatHang(order.getNgayDatHang());
        dto.setTongTien(order.getTongTien());
        dto.setTrangThai(order.getTrangThai().name());
        dto.setTenNguoiNhan(order.getTenNguoiNhan());
        dto.setDiaChiGiaoHang(order.getDiaChiGiaoHang());
        dto.setSdt(order.getSdt());

        if (order.getNhnUser() != null) {
            dto.setUserId(order.getNhnUser().getId());
            dto.setUsername(order.getNhnUser().getUsername());
        }

        // Map Items
        if (order.getNhnDonhangItems() != null) {
            List<com.project3.chelamthachxa.nhndto.NhnOrderDTO.NhnOrderItemDTO> itemDTOs = order.getNhnDonhangItems().stream().map(item -> {
                com.project3.chelamthachxa.nhndto.NhnOrderDTO.NhnOrderItemDTO itemDTO = new com.project3.chelamthachxa.nhndto.NhnOrderDTO.NhnOrderItemDTO();
                itemDTO.setProductId(item.getNhnSanpham().getId());
                itemDTO.setProductName(item.getNhnSanpham().getTenSanPham());
                itemDTO.setQuantity(item.getSoLuong());
                itemDTO.setPrice(item.getGiaBan());
                itemDTO.setImageUrl(item.getNhnSanpham().getImageUrl());
                return itemDTO;
            }).collect(java.util.stream.Collectors.toList());
            dto.setItems(itemDTOs);
        }
        return dto;
    }

    // Lấy đơn hàng theo ID (Entity)
    public Optional<NhnDonhang> findOrderById(Long id) {
        return nhnDonhangRepository.findById(id);
    }

    // Lấy đơn hàng theo ID (DTO)
    @Transactional
    public Optional<com.project3.chelamthachxa.nhndto.NhnOrderDTO> findOrderDTOById(Long id) {
        return nhnDonhangRepository.findById(id).map(this::convertToDTO);
    }

    // Lấy đơn hàng theo NhnUser ID
    public List<NhnDonhang> findOrdersByUserId(Long userId) {
        // Dòng này đã được sửa để gọi phương thức Repository tìm kiếm theo ID Người dùng,
        // trả về List<NhnDonhang> như mong đợi.
        return nhnDonhangRepository.findByNhnUser_Id(userId);
    }

    // CREATE: Tạo đơn hàng mới
    @Transactional
    public NhnDonhang createOrder(NhnOrderCreateRequest request) {
        NhnUser nhnUser = nhnUserRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));

        NhnDonhang nhnDonhang = new NhnDonhang();
        nhnDonhang.setNhnUser(nhnUser);
        nhnDonhang.setTenNguoiNhan(request.getTenNguoiNhan());
        nhnDonhang.setDiaChiGiaoHang(request.getDiaChiGiaoHang());
        nhnDonhang.setSdt(request.getSdt());
        nhnDonhang.setTrangThai(NhnDonhang.TrangThaiDonHang.PENDING);

        Set<NhnDonhangItem> items = new HashSet<>();
        BigDecimal tongTien = BigDecimal.ZERO;

        for (NhnOrderItemRequest itemRequest : request.getItems()) {
            NhnSanpham nhnSanpham = nhnSanphamRepository.findById(itemRequest.getSanphamId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + itemRequest.getSanphamId()));

            if (nhnSanpham.getSoLuongTon() < itemRequest.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + nhnSanpham.getTenSanPham() + " không đủ số lượng.");
            }

            NhnDonhangItem item = new NhnDonhangItem();
            item.setNhnDonhang(nhnDonhang);
            item.setNhnSanpham(nhnSanpham);
            item.setSoLuong(itemRequest.getSoLuong());
            item.setGiaBan(nhnSanpham.getGia());

            BigDecimal subtotal = nhnSanpham.getGia().multiply(BigDecimal.valueOf(itemRequest.getSoLuong()));
            tongTien = tongTien.add(subtotal);

            items.add(item);

            // Cập nhật số lượng tồn kho
            nhnSanpham.setSoLuongTon(nhnSanpham.getSoLuongTon() - itemRequest.getSoLuong());
            nhnSanphamRepository.save(nhnSanpham);
        }

        nhnDonhang.setNhnDonhangItems(items);
        nhnDonhang.setTongTien(tongTien);

        return nhnDonhangRepository.save(nhnDonhang);
    }

    // UPDATE: Cập nhật trạng thái đơn hàng (Admin)
    // UPDATE: Cập nhật trạng thái đơn hàng (Admin)
    @Transactional
    public NhnDonhang updateOrderStatus(Long orderId, NhnDonhang.TrangThaiDonHang newStatus) {
        return nhnDonhangRepository.findById(orderId)
                .map(donhang -> {
                    donhang.setTrangThai(newStatus);
                    return nhnDonhangRepository.save(donhang);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đơn hàng với ID: " + orderId));
    }

    // UPDATE: Cập nhật trạng thái đơn hàng (Trả về DTO)
    @Transactional
    public com.project3.chelamthachxa.nhndto.NhnOrderDTO updateOrderStatusDTO(Long orderId, NhnDonhang.TrangThaiDonHang newStatus) {
        NhnDonhang updatedOrder = updateOrderStatus(orderId, newStatus);
        return convertToDTO(updatedOrder);
    }
}