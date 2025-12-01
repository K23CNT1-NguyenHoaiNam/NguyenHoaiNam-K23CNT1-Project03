package com.project3.chelamthachxa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List; // SỬA: Thay thế Set bằng List
import org.springframework.security.core.userdetails.UserDetails; // CÂN NHẮC THÊM: Implement UserDetails
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "nguoidung")
@Data
@NoArgsConstructor
@AllArgsConstructor
// UserDetails implementation là không cần thiết nếu dùng CustomUserDetailsService,
// nhưng chúng ta sẽ giữ code sạch để User có thể cung cấp Authorities nếu cần
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String hoten;
    private String diachi;
    private String sdt;

    // Thiết lập quan hệ N-N với Vaitro
    // SỬA: Dùng List thay vì Set để tránh lỗi Hashcode/Equals trong Hibernate
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Thay ALL bằng PERSIST/MERGE
    @JoinTable(
            name = "user_vaitro",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vaitro_id")
    )
    private List<Vaitro> vaiTro; // SỬA: Dùng List

    // Thêm quan hệ với Đơn hàng (1 User có nhiều Donhang)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donhang> donhangs; // SỬA: Dùng List

    // --------------------------------------------------------------------------------
    // Implement UserDetails methods (Bắt buộc nếu implements UserDetails)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.vaiTro.stream()
                .map(role -> new SimpleGrantedAuthority(role.getTen()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    // --------------------------------------------------------------------------------
}