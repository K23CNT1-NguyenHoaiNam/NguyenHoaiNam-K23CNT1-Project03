package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhndto.NhnRegisterRequest;
import com.project3.chelamthachxa.nhnentity.NhnUser;
import com.project3.chelamthachxa.nhnentity.NhnVaitro;
import com.project3.chelamthachxa.nhnrepository.NhnUserRepository;
import com.project3.chelamthachxa.nhnrepository.NhnVaitroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet; // Import HashSet
import java.util.Set; // Import Set
import java.util.Arrays;

@Service
public class NhnAuthService {

    @Autowired
    private NhnVaitroRepository nhnVaitroRepository;

    @Autowired
    private NhnUserRepository nhnUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public NhnUser registerUser(NhnRegisterRequest request) {
        // Kiểm tra username/email đã tồn tại chưa
        if (nhnUserRepository.existsByUsername(request.getUsername()) || nhnUserRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Tên đăng nhập hoặc Email đã tồn tại!");
        }

        // Tạo vai trò mặc định là USER
        NhnVaitro userRole = nhnVaitroRepository.findByTen("ROLE_USER")
                .orElseGet(() -> {
                    // Nếu chưa tồn tại, tạo mới
                    NhnVaitro newRole = new NhnVaitro(null, "ROLE_USER");
                    return nhnVaitroRepository.save(newRole);
                });

        // Tạo người dùng mới
        NhnUser newNhnUser = new NhnUser();
        newNhnUser.setUsername(request.getUsername());
        newNhnUser.setEmail(request.getEmail());
        newNhnUser.setHoten(request.getHoten());
        // Mã hóa mật khẩu trước khi lưu
        newNhnUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // KHẮC PHỤC LỖI BIÊN DỊCH: Sử dụng Set và HashSet để khớp với NhnUser Entity
        Set<NhnVaitro> roles = new HashSet<>(Arrays.asList(userRole));
        newNhnUser.setVaiTro(roles);

        return nhnUserRepository.save(newNhnUser);
    }
}