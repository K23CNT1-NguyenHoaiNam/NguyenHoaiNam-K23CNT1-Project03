package com.project3.chelamthachxa.nhnentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set; // Dùng Set cho các mối quan hệ
import java.util.stream.Collectors;

@Entity
@Table(name = "nhn_nguoidung")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhnUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nhn_nguoidung_id")
    private Long id;

    @Column(name = "nhn_username", nullable = false, unique = true)
    private String username;

    @Column(name = "nhn_email", nullable = false, unique = true)
    private String email;
    @Column(name = "nhn_password", nullable = false)

    private String password;
    @Column(name = "nhn_hoten")
    private String hoten;
    @Column(name = "nhn_diachi")
    private String diachi;
    @Column(name = "nhn_sdt")
    private String sdt;

    // Thiết lập quan hệ N-N với NhnVaitro
    // SỬA: Chuyển từ List sang Set để tránh xung đột @Data/JPA
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "nhn_user_vaitro",
            joinColumns = @JoinColumn(name = "nhn_user_id"),
            inverseJoinColumns = @JoinColumn(name = "nhn_vaitro_id")
    )
    private Set<NhnVaitro> vaiTro; // ĐÃ SỬA: Dùng Set

    // Thêm quan hệ với Đơn hàng (1 NhnUser có nhiều NhnDonhang)
    // SỬA: Chuyển từ List sang Set
    @OneToMany(mappedBy = "nhnUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore // Prevent infinite recursion
    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    private Set<NhnDonhang> nhnDonhangs; // ĐÃ SỬA: Dùng Set

    // --------------------------------------------------------------------------------
    // Implement UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.vaiTro == null || this.vaiTro.isEmpty()) {
            return Collections.emptyList();
        }

        return this.vaiTro.stream()
                .map(role -> new SimpleGrantedAuthority(role.getTen()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}