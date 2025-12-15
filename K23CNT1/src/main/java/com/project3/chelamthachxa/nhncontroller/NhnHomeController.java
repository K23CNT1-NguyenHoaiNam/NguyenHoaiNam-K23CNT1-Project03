package com.project3.chelamthachxa.nhncontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NhnHomeController {

    // Xử lý đường dẫn gốc "/"
    @GetMapping("/nhnindex")
    public String home() {
        // Trả về tên của template (nhnindex.html) trong thư mục /resources/templates/
        return "nhnindex";
    }
}