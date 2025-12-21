package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhndto.NhnUserUpdateRequest;
import com.project3.chelamthachxa.nhnentity.NhnUser;
import com.project3.chelamthachxa.nhnrepository.NhnUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

@Service
public class NhnUserService {

    @Autowired
    private NhnUserRepository nhnUserRepository;

    // Lấy tất cả người dùng
    public List<NhnUser> findAllUsers() {
        return nhnUserRepository.findAll();
    }

    // ADMIN: Lấy tất cả người dùng (DTO)
    @Transactional
    public List<com.project3.chelamthachxa.nhndto.NhnUserDTO> findAllUserDTOs() {
        return nhnUserRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    private com.project3.chelamthachxa.nhndto.NhnUserDTO convertToDTO(NhnUser user) {
        com.project3.chelamthachxa.nhndto.NhnUserDTO dto = new com.project3.chelamthachxa.nhndto.NhnUserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setHoten(user.getHoten());
        dto.setSdt(user.getSdt());
        dto.setDiachi(user.getDiachi());
        
        if (user.getVaiTro() != null) {
            dto.setRoleNames(user.getVaiTro().stream()
                    .map(com.project3.chelamthachxa.nhnentity.NhnVaitro::getTen)
                    .collect(java.util.stream.Collectors.toList()));
        }
        return dto;
    }

    // Lấy người dùng theo ID
    public Optional<NhnUser> findUserById(Long id) {
        return nhnUserRepository.findById(id);
    }

    // Lấy người dùng theo Username (Dùng cho security/controller)
    public Optional<NhnUser> findByUsername(String username) {
        return nhnUserRepository.findByUsername(username);
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