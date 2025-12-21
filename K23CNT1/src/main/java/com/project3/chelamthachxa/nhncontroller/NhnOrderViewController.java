package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhnentity.NhnDonhang;
import com.project3.chelamthachxa.nhnentity.NhnUser;
import com.project3.chelamthachxa.nhnservice.NhnDonhangService;
import com.project3.chelamthachxa.nhnservice.NhnUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/nhndonhang")
public class NhnOrderViewController {

    @Autowired
    private NhnDonhangService nhnDonhangService;

    @Autowired
    private NhnUserService nhnUserService;

    @GetMapping
    public String viewOrderHistory(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        String username = authentication.getName();
        NhnUser user = nhnUserService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<NhnDonhang> orders = nhnDonhangService.findOrdersByUserId(user.getId());
        model.addAttribute("orders", orders);

        return "nhndonhang";
    }
}
