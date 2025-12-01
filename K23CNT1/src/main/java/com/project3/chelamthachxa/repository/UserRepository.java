package com.project3.chelamthachxa.repository;

import com.project3.chelamthachxa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Lớp này dùng để thao tác với bảng Người dùng trong CSDL
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}