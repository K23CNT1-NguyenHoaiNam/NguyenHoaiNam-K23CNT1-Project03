package com.project3.chelamthachxa.controller;

import com.project3.chelamthachxa.entity.Sanpham;
import com.project3.chelamthachxa.service.SanphamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductViewController {

    @Autowired
    private SanphamService sanphamService;

    /**
     * Xử lý yêu cầu GET /products để hiển thị danh sách sản phẩm.
     * @param model Đối tượng Model để truyền dữ liệu sang Thymeleaf.
     * @return Tên template products.html.
     */
    @GetMapping
    public String listProducts(Model model) {
        // Lấy tất cả sản phẩm từ cơ sở dữ liệu
        List<Sanpham> products = sanphamService.findAll();

        // Thêm danh sách sản phẩm vào Model để hiển thị trên template
        model.addAttribute("products", products);

        // Trả về template products.html (phải nằm trong /resources/templates/)
        return "products";
    }
}