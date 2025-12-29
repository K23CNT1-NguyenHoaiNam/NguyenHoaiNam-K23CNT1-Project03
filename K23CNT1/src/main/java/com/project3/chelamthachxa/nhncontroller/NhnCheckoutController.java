package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhnmodel.NhnCartItem;
import com.project3.chelamthachxa.nhndto.NhnOrderCreateRequest;
import com.project3.chelamthachxa.nhndto.NhnOrderItemRequest;
import com.project3.chelamthachxa.nhnentity.NhnUser;
import com.project3.chelamthachxa.nhnentity.NhnDonhang;
import com.project3.chelamthachxa.nhnservice.NhnCartService;
import com.project3.chelamthachxa.nhnservice.NhnDonhangService;
import com.project3.chelamthachxa.nhnservice.NhnUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/nhncheckout")
public class NhnCheckoutController {

    @Autowired
    private NhnCartService nhnCartService;

    @Autowired
    private NhnDonhangService nhnDonhangService;
    
    @Autowired
    private NhnUserService nhnUserService;

    @GetMapping
    public String viewCheckout(HttpSession session, Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        List<NhnCartItem> cartItems = nhnCartService.getCartItems(session);
        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/nhncart";
        }

        BigDecimal totalAmount = BigDecimal.valueOf(nhnCartService.getTotalAmount(session));

        // Pre-fill user info if available
        String username = authentication.getName();
        nhnUserService.findByUsername(username).ifPresent(user -> {
            model.addAttribute("user", user);
        });

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);

        return "nhncheckout";
    }

    @PostMapping("/process")
    public String processCheckout(@RequestParam("tenNguoiNhan") String tenNguoiNhan,
                                  @RequestParam("sdt") String sdt,
                                  @RequestParam("diaChiGiaoHang") String diaChiGiaoHang,
                                  @RequestParam(value = "paymentMethod", defaultValue = "COD") String paymentMethod,
                                  HttpSession session,
                                  Authentication authentication) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        List<NhnCartItem> cartItems = nhnCartService.getCartItems(session);
        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/nhncart";
        }

        String username = authentication.getName();
        NhnUser user = nhnUserService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create Order Request
        NhnOrderCreateRequest request = new NhnOrderCreateRequest();
        request.setUserId(user.getId());
        request.setTenNguoiNhan(tenNguoiNhan);
        request.setSdt(sdt);
        request.setDiaChiGiaoHang(diaChiGiaoHang);
        
        // Map Cart Items to Order Items
        List<NhnOrderItemRequest> orderItems = new ArrayList<>();
        for (NhnCartItem cartItem : cartItems) {
            NhnOrderItemRequest itemRequest = new NhnOrderItemRequest();
            itemRequest.setSanphamId(cartItem.getProductId());
            itemRequest.setSoLuong(cartItem.getQuantity());
            orderItems.add(itemRequest);
        }
        request.setPaymentMethod(paymentMethod);
        request.setItems(orderItems);

        // Save Order
        NhnDonhang order = nhnDonhangService.createOrder(request);

        // Clear Cart
        nhnCartService.clearCart(session);

        if ("TRANSFER".equals(paymentMethod)) {
            return "redirect:/nhncheckout/payment?orderId=" + order.getId();
        }

        return "redirect:/nhndonhang";
    }

    @GetMapping("/payment")
    public String viewPayment(@RequestParam("orderId") Long orderId, Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        NhnDonhang order = nhnDonhangService.findOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getNhnUser().getUsername().equals(authentication.getName())) {
            return "redirect:/nhnindex";
        }

        model.addAttribute("order", order);
        return "nhnqr_payment";
    }
}
