package com.univcursor.web.dto;

import com.univcursor.domain.CartItem;

import java.time.LocalDateTime;

public record CartItemResponse(
        Long courseId,
        CourseResponse course,
        LocalDateTime createdAt
) {
    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(cartItem.getCourse().getId(), CourseResponse.from(cartItem.getCourse()), cartItem.getCreatedAt());
    }
}
