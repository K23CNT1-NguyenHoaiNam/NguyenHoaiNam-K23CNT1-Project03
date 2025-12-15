package com.project3.chelamthachxa;

import com.project3.chelamthachxa.nhnentity.NhnUser;
import com.project3.chelamthachxa.nhnentity.NhnVaitro;
import com.project3.chelamthachxa.nhnrepository.NhnUserRepository;
import com.project3.chelamthachxa.nhnrepository.NhnVaitroRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet; // SỬA: Import HashSet
import java.util.Optional;
import java.util.Set; // SỬA: Import Set

@Component
public class NhnAdminInitializer implements CommandLineRunner {

    private final NhnUserRepository nhnUserRepository;
    private final NhnVaitroRepository nhnVaitroRepository;
    private final PasswordEncoder passwordEncoder;

    public NhnAdminInitializer(NhnUserRepository nhnUserRepository, NhnVaitroRepository nhnVaitroRepository, PasswordEncoder passwordEncoder) {
        this.nhnUserRepository = nhnUserRepository;
        this.nhnVaitroRepository = nhnVaitroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        NhnVaitro userRole = nhnVaitroRepository.findByTen("ROLE_USER")
                .orElseGet(() -> nhnVaitroRepository.save(new NhnVaitro(null, "ROLE_USER")));

        NhnVaitro adminRole = nhnVaitroRepository.findByTen("ROLE_ADMIN")
                .orElseGet(() -> nhnVaitroRepository.save(new NhnVaitro(null, "ROLE_ADMIN")));

        // --- 2. Kiểm tra và Tạo NhnUser Admin ---
        Optional<NhnUser> adminOptional = nhnUserRepository.findByUsername("admin");

        if (adminOptional.isEmpty()) {
            System.out.println(">>> KHỞI TẠO: Tạo người dùng ADMIN mặc định.");

            NhnUser admin = new NhnUser();
            admin.setUsername("admin");
            admin.setEmail("admin@store.com");
            admin.setHoten("Quản trị viên");

            admin.setPassword(passwordEncoder.encode("123456"));

            // SỬA: Gán vai trò bằng Set thay vì List
            Set<NhnVaitro> adminRoles = new HashSet<>(Arrays.asList(adminRole));
            admin.setVaiTro(adminRoles);

            nhnUserRepository.save(admin);
            System.out.println(">>> Khởi tạo ADMIN thành công. Mật khẩu: 123456");
        } else {
            System.out.println(">>> Người dùng ADMIN đã tồn tại. Bỏ qua khởi tạo.");
        }
    }
}