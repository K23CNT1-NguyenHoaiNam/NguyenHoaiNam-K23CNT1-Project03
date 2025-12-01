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
        // SỬA: Thay đổi đường dẫn template thành "auth/register"
        return "auth/register";
    }

    // Xử lý logic Đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest request, Model model) {
        try {
            authService.registerUser(request);
            // SỬA: Redirect đến trang đăng nhập
            return "redirect:/auth/login?success=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerRequest", request);
            // SỬA: Quay lại template trong thư mục con
            return "auth/register";
        }
    }

    // Endpoint hiển thị trang Đăng nhập
    @GetMapping("/login")
    public String showLoginForm() {
        // SỬA: Thay đổi đường dẫn template thành "auth/login"
        return "auth/login";
    }
}