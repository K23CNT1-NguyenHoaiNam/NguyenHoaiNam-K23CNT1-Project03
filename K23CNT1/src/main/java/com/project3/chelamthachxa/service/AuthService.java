package com.project3.chelamthachxa.service;

import com.project3.chelamthachxa.dto.RegisterRequest;
import com.project3.chelamthachxa.entity.User;
import com.project3.chelamthachxa.entity.Vaitro;
import com.project3.chelamthachxa.repository.UserRepository;
import com.project3.chelamthachxa.repository.VaitroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// SỬA: Thay thế import java.util.Set và java.util.HashSet
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Service
public class AuthService {

    @Autowired
    private VaitroRepository vaitroRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegisterRequest request) {
        // Kiểm tra username/email đã tồn tại chưa
        if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Tên đăng nhập hoặc Email đã tồn tại!");
        }

        // Tạo vai trò mặc định là USER
        Vaitro userRole = vaitroRepository.findByTen("ROLE_USER")
                .orElseGet(() -> {
                    // Nếu chưa tồn tại, tạo mới
                    Vaitro newRole = new Vaitro(null, "ROLE_USER");
                    return vaitroRepository.save(newRole);
                });

        // Tạo người dùng mới
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setHoten(request.getHoten());
        // Mã hóa mật khẩu trước khi lưu
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // SỬA LỖI: Gán vai trò bằng List thay vì Set
        List<Vaitro> roles = new ArrayList<>(Arrays.asList(userRole));
        newUser.setVaiTro(roles);

        return userRepository.save(newUser);
    }
}