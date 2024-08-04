package com.gear2go_frontend.domain;

import java.math.BigDecimal;

public record CartItem(Long productId, Integer quantity, BigDecimal price) {
}
