package com.project3.chelamthachxa.nhncontroller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller xử lý các yêu cầu liên quan đến trang quản trị (Admin Dashboard).
 * Yêu cầu người dùng phải có vai trò ROLE_ADMIN.
 */
@Controller
@RequestMapping("/admin")
public class NhnAdminController {

    /**
     * Hiển thị trang Admin Dashboard.
     * Phương thức này đã được bảo vệ bởi Spring Security trong NhnSecurityConfig
     * bằng .requestMatchers("/admin/**").hasRole("ADMIN").
     *
     * @return Tên template nhnadmin_dashboard.html
     */
    @GetMapping("/nhnadmin_dashboard")
    public String showAdminDashboard() {
        return "nhnadmin_dashboard";
    }
}