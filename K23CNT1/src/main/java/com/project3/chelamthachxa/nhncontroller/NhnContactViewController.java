package com.project3.chelamthachxa.nhncontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller để xử lý các yêu cầu liên quan đến trang Liên hệ.
 * Tách biệt khỏi HomeController để quản lý code rõ ràng hơn.
 */
@Controller
public class NhnContactViewController {

    /**
     * Phương thức xử lý hiển thị trang Liên hệ.
     * URL: /lien-he
     * * @return Tên view template (nhncontact.html)
     */
    @GetMapping("/nhncontact")
    public String viewContactPage() {
        // Trả về tên view là "nhncontact" (ứng với tệp nhncontact.html trong thư mục templates)
        return "nhncontact";
    }
}