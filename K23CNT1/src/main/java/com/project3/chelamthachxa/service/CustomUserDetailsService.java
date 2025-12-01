package com.project3.chelamthachxa.service;

import com.project3.chelamthachxa.entity.User;
import com.project3.chelamthachxa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // SỬ DỤNG CONSTRUCTOR INJECTION
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Phương thức bắt buộc mà Spring Security gọi để tải thông tin người dùng
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tìm kiếm người dùng trong cơ sở dữ liệu
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với username: " + username));

        // 2. Do chúng ta đã sửa Entity User để implement UserDetails, chúng ta trả về User trực tiếp
        // Điều này đảm bảo UserDetails đã bao gồm cả Username, Password và Authorities (Vai trò)
        return user;
    }
}