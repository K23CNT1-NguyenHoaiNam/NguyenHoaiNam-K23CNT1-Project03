package com.project3.chelamthachxa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Xử lý đường dẫn gốc "/"
    @GetMapping("/")
    public String home() {
        // Trả về tên của template (index.html) trong thư mục /resources/templates/
        return "index";
    }
}