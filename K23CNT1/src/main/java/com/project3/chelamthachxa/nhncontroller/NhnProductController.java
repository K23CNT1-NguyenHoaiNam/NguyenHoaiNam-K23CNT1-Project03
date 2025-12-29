package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhndto.NhnProductDTO;
import com.project3.chelamthachxa.nhnservice.NhnProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NhnProductController {

    @Autowired
    private NhnProductService productService;

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        NhnProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/detail"; // src/main/resources/templates/product/detail.html
    }
}
