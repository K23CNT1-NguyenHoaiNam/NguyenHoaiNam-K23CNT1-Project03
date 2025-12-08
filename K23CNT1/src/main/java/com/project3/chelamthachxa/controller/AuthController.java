package com.project3.chelamthachxa.controller;

import com.project3.chelamthachxa.dto.RegisterRequest;
import com.project3.chelamthachxa.entity.User;
import com.project3.chelamthachxa.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // Dùng cho Thymeleaf

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint hiển thị trang Đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        // Sử dụng tên template hợp lệ
        return "auth/register";
    }

    // Xử lý logic Đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest request, Model model) {
        try {
            authService.registerUser(request);
            // Redirect đến URL Controller chính xác
            return "redirect:/auth/login?success=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerRequest", request);
            return "auth/register";
        }
    }

    // Endpoint hiển thị trang Đăng nhập
    @GetMapping("/login")
    public String showLoginForm() {
        // Sử dụng tên template hợp lệ
        return "auth/login";
    }
}