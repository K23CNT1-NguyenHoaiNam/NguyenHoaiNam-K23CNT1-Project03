package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhnservice.NhnCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/nhncart")
public class NhnCartController {

    @Autowired
    private NhnCartService cartService;

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("cartItems", cartService.getCartItems(session));
        model.addAttribute("totalAmount", cartService.getTotalAmount(session));
        return "nhncart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {
        cartService.addToCart(session, productId, quantity);
        return "redirect:/nhncart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("productId") Long productId,
                             @RequestParam("quantity") int quantity,
                             HttpSession session) {
        cartService.updateQuantity(session, productId, quantity);
        return "redirect:/nhncart";
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable("productId") Long productId, HttpSession session) {
        cartService.removeFromCart(session, productId);
        return "redirect:/nhncart";
    }
}
