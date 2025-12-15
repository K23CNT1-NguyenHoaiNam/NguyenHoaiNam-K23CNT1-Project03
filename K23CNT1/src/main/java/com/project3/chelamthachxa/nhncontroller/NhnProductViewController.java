package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhnentity.NhnSanpham;
import com.project3.chelamthachxa.nhnservice.NhnSanphamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/nhnproducts")
public class NhnProductViewController {

    @Autowired
    private NhnSanphamService nhnSanphamService;

    /**
     * Xử lý yêu cầu GET /products để hiển thị danh sách sản phẩm.
     * @param model Đối tượng Model để truyền dữ liệu sang Thymeleaf.
     * @return Tên template nhnproducts.html.
     */
    @GetMapping
    public String listProducts(Model model) {
        // Lấy tất cả sản phẩm từ cơ sở dữ liệu
        List<NhnSanpham> products = nhnSanphamService.findAll();

        // Thêm danh sách sản phẩm vào Model để hiển thị trên template
        model.addAttribute("nhnproducts", products);

        // Trả về template nhnproducts.html (phải nằm trong /resources/templates/)
        return "nhnproducts";
    }
}