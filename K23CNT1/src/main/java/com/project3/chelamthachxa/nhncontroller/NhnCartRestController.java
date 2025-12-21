package com.project3.chelamthachxa.nhncontroller;

import com.project3.chelamthachxa.nhnmodel.NhnCartItem;
import com.project3.chelamthachxa.nhnservice.NhnCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class NhnCartRestController {

    @Autowired
    private NhnCartService nhnCartService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestParam("productId") Long productId,
                                                         @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                                         HttpSession session) {
        // Add to cart
        nhnCartService.addToCart(session, productId, quantity);

        // Get updated cart state
        List<NhnCartItem> cartItems = nhnCartService.getCartItems(session);
        int totalItems = nhnCartService.getCartCount(session); // You might need to add this method or calc it
        double totalAmount = nhnCartService.getTotalAmount(session);

        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("totalItems", totalItems);
        response.put("totalAmount", totalAmount);
        response.put("cartItems", cartItems);

        return ResponseEntity.ok(response);
    }
}
