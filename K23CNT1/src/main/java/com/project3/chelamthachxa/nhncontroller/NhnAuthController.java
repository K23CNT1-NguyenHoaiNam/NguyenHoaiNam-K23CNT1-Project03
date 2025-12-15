package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhndto.NhnRegisterRequest;
import com.project3.chelamthachxa.nhnservice.NhnAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model; // Dùng cho Thymeleaf

@Controller
@RequestMapping("/auth")
public class NhnAuthController {

    @Autowired
    private NhnAuthService nhnAuthService;

    // Endpoint hiển thị trang Đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new NhnRegisterRequest());
        // Sử dụng tên template hợp lệ
        return "auth/register";
    }

    // Xử lý logic Đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute NhnRegisterRequest request, Model model) {
        try {
            nhnAuthService.registerUser(request);
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