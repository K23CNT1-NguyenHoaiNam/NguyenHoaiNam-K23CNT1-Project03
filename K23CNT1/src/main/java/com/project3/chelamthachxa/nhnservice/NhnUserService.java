package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhndto.NhnUserUpdateRequest;
import com.project3.chelamthachxa.nhnentity.NhnUser;
import com.project3.chelamthachxa.nhnrepository.NhnUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhnUserService {

    @Autowired
    private NhnUserRepository nhnUserRepository;

    // Lấy tất cả người dùng
    public List<NhnUser> findAllUsers() {
        return nhnUserRepository.findAll();
    }

    // Lấy người dùng theo ID
    public Optional<NhnUser> findUserById(Long id) {
        return nhnUserRepository.findById(id);
    }

    // Cập nhật thông tin người dùng (Admin)
    public NhnUser updateUser(Long id, NhnUserUpdateRequest updateRequest) {
        return nhnUserRepository.findById(id)
                .map(user -> {
                    user.setHoten(updateRequest.getHoten());
                    user.setEmail(updateRequest.getEmail());
                    user.setDiachi(updateRequest.getDiachi());
                    user.setSdt(updateRequest.getSdt());
                    return nhnUserRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Người dùng với ID: " + id));
    }

    // Xóa người dùng (Admin)
    public void deleteUser(Long id) {
        if (nhnUserRepository.existsById(id)) {
            nhnUserRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy Người dùng với ID: " + id);
        }
    }
}