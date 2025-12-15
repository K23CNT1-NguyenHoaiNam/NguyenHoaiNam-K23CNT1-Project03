package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhnentity.NhnUser;
import com.project3.chelamthachxa.nhnrepository.NhnUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NhnCustomUserDetailsService implements UserDetailsService {

    private final NhnUserRepository nhnUserRepository;

    // SỬ DỤNG CONSTRUCTOR INJECTION
    public NhnCustomUserDetailsService(NhnUserRepository nhnUserRepository) {
        this.nhnUserRepository = nhnUserRepository;
    }

    // Phương thức bắt buộc mà Spring Security gọi để tải thông tin người dùng
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tìm kiếm người dùng trong cơ sở dữ liệu
        NhnUser nhnUser = nhnUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với username: " + username));

        // 2. Do chúng ta đã sửa Entity NhnUser để implement UserDetails, chúng ta trả về NhnUser trực tiếp
        // Điều này đảm bảo UserDetails đã bao gồm cả Username, Password và Authorities (Vai trò)
        return nhnUser;
    }
}