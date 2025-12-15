package com.project3.chelamthachxa.nhnrepository;

import com.project3.chelamthachxa.nhnentity.NhnVaitro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Lớp này dùng để thao tác với bảng Vai trò trong CSDL
public interface NhnVaitroRepository extends JpaRepository<NhnVaitro, Long> {
    Optional<NhnVaitro> findByTen(String ten);
}