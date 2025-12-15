package com.project3.chelamthachxa.nhnrepository;

import com.project3.chelamthachxa.nhnentity.NhnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Lớp này dùng để thao tác với bảng Người dùng trong CSDL
public interface NhnUserRepository extends JpaRepository<NhnUser, Long> {
    Optional<NhnUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}