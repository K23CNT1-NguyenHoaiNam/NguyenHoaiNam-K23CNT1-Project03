package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhnentity.NhnSanpham;
import com.project3.chelamthachxa.nhnrepository.NhnSanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhnSanphamService {

    @Autowired
    private NhnSanphamRepository nhnSanphamRepository;

    // Lấy tất cả sản phẩm
    public List<NhnSanpham> findAll() {
        return nhnSanphamRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<NhnSanpham> findById(Long id) {
        return nhnSanphamRepository.findById(id);
    }

    // Thêm mới/Cập nhật sản phẩm
    public NhnSanpham save(NhnSanpham nhnSanpham) {
        return nhnSanphamRepository.save(nhnSanpham);
    }

    // Xóa sản phẩm
    public void deleteById(Long id) {
        if (nhnSanphamRepository.existsById(id)) {
            nhnSanphamRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy Sản phẩm với ID: " + id);
        }
    }
}