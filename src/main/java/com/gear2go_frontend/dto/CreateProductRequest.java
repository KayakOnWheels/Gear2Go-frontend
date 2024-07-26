package com.gear2go_frontend.dto;

import java.math.BigDecimal;

public record CreateProductRequest(String name, Float weight, BigDecimal price, Integer stock) {
}
