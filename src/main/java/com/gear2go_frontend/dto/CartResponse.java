package com.gear2go_frontend.dto;

import com.gear2go_frontend.domain.CartItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CartResponse(Long id, Long userId, LocalDate rentDate, LocalDate returnDate, List<CartItem> cartItemResponseList, BigDecimal totalPrice) {
}
