package com.univcursor.web;

import com.univcursor.config.security.UserPrincipal;
import com.univcursor.service.CartService;
import com.univcursor.web.dto.CartItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Page<CartItemResponse> list(@AuthenticationPrincipal UserPrincipal principal,
                                       @PageableDefault(size = 20) Pageable pageable) {
        return cartService.getCartItems(principal.getUser().getId(), pageable).map(CartItemResponse::from);
    }

    @PostMapping("/{courseId}")
    public void add(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long courseId) {
        cartService.addCourse(principal.getUser(), courseId);
    }

    @DeleteMapping("/{courseId}")
    public void remove(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long courseId) {
        cartService.removeCourse(principal.getUser().getId(), courseId);
    }
}
