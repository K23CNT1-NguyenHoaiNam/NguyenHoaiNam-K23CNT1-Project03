package com.project3.chelamthachxa;

import com.project3.chelamthachxa.entity.User;
import com.project3.chelamthachxa.entity.Vaitro;
import com.project3.chelamthachxa.repository.UserRepository;
import com.project3.chelamthachxa.repository.VaitroRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList; // SỬA: Import ArrayList
import java.util.Arrays;
import java.util.List; // SỬA: Import List
import java.util.Optional;
import java.util.Set;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VaitroRepository vaitroRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, VaitroRepository vaitroRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.vaitroRepository = vaitroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        Vaitro userRole = vaitroRepository.findByTen("ROLE_USER")
                .orElseGet(() -> vaitroRepository.save(new Vaitro(null, "ROLE_USER")));

        Vaitro adminRole = vaitroRepository.findByTen("ROLE_ADMIN")
                .orElseGet(() -> vaitroRepository.save(new Vaitro(null, "ROLE_ADMIN")));

        // --- 2. Kiểm tra và Tạo User Admin ---
        Optional<User> adminOptional = userRepository.findByUsername("admin");

        if (adminOptional.isEmpty()) {
            System.out.println(">>> KHỞI TẠO: Tạo người dùng ADMIN mặc định.");

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@store.com");
            admin.setHoten("Quản trị viên");

            admin.setPassword(passwordEncoder.encode("123456"));

            // SỬA: Gán vai trò bằng List thay vì Set
            List<Vaitro> adminRoles = new ArrayList<>(Arrays.asList(adminRole));
            admin.setVaiTro(adminRoles);

            userRepository.save(admin);
            System.out.println(">>> Khởi tạo ADMIN thành công. Mật khẩu: 123456");
        } else {
            System.out.println(">>> Người dùng ADMIN đã tồn tại. Bỏ qua khởi tạo.");
        }
    }
}