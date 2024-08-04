package com.gear2go_frontend.dto;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(force = true)
public record ProductResponse(Long id, String name, Float weight, BigDecimal price, Integer stock, String imageUrl) {
}
