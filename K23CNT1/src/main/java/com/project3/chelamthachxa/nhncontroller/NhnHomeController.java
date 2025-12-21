package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhnentity.NhnSanpham;
import com.project3.chelamthachxa.nhnrepository.NhnSanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class NhnHomeController {

    @Autowired
    private NhnSanphamRepository nhnSanphamRepository;

    @GetMapping
    public String index(Model model) {
        return "redirect:/nhnindex";
    }

    @GetMapping("/nhnindex")
    public String nhnIndex(Model model) {
        // Lấy danh sách sản phẩm hiển thị ở trang chủ
        model.addAttribute("nhnproducts", nhnSanphamRepository.findAll());
        return "nhnindex";
    }

    @RequestMapping("/nhnnews")
    public String nhnNews() {
        return "nhnnews";
    }
}