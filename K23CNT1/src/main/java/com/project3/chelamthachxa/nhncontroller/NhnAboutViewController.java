package com.project3.chelamthachxa.nhncontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller xử lý các yêu cầu ánh xạ đến các trang HTML tĩnh
 * (View) liên quan đến Giới Thiệu (/nhnabout).
 *
 * Lưu ý: Ánh xạ /nhnindex và /nhnproducts đã được loại bỏ để tránh xung đột
 * với các Controller chuyên trách khác (như NhnHomeController và NhnProductViewController).
 */
@Controller
public class NhnAboutViewController {

    /**
     * Xử lý yêu cầu GET đến đường dẫn "/nhnabout"
     * và trả về tên template "nhnabout".
     *
     * @return Tên template Thymeleaf (nhnabout.html)
     */
    @GetMapping("/nhnabout")
    public String showAboutPage() {
        // Trả về tên file template "nhnabout.html" (nằm trong thư mục src/main/resources/templates)
        return "nhnabout";
    }

}