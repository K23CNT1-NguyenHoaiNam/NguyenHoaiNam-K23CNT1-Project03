package com.project3.chelamthachxa.nhnservice;

import com.project3.chelamthachxa.nhnmodel.NhnCartItem;
import com.project3.chelamthachxa.nhnentity.NhnSanpham;
import com.project3.chelamthachxa.nhnrepository.NhnSanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class NhnCartService {

    @Autowired
    private NhnSanphamRepository sanphamRepository;

    private static final String CART_SESSION_KEY = "NHN_CART";

    public List<NhnCartItem> getCartItems(HttpSession session) {
        List<NhnCartItem> cartItems = (List<NhnCartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cartItems);
        }
        return cartItems;
    }

    public void addToCart(HttpSession session, Long productId, int quantity) {
        List<NhnCartItem> cartItems = getCartItems(session);
        Optional<NhnCartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            NhnCartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            NhnSanpham sanpham = sanphamRepository.findById(productId).orElse(null);
            if (sanpham != null) {
                NhnCartItem newItem = new NhnCartItem(
                        sanpham.getId(),
                        sanpham.getTenSanPham(),
                        sanpham.getImageUrl(),
                        quantity,
                        sanpham.getGia().doubleValue()
                );
                cartItems.add(newItem);
            }
        }
        session.setAttribute(CART_SESSION_KEY, cartItems);
    }

    public void updateQuantity(HttpSession session, Long productId, int quantity) {
        List<NhnCartItem> cartItems = getCartItems(session);
        cartItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
        session.setAttribute(CART_SESSION_KEY, cartItems);
    }

    public void removeFromCart(HttpSession session, Long productId) {
        List<NhnCartItem> cartItems = getCartItems(session);
        cartItems.removeIf(item -> item.getProductId().equals(productId));
        session.setAttribute(CART_SESSION_KEY, cartItems);
    }

    public int getCartCount(HttpSession session) {
        return getCartItems(session).stream().mapToInt(NhnCartItem::getQuantity).sum();
    }

    public double getTotalAmount(HttpSession session) {
        return getCartItems(session).stream().mapToDouble(NhnCartItem::getTotalPrice).sum();
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
