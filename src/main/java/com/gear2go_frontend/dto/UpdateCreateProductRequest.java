package com.gear2go_frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateCreateProductRequest {
    private Long id;
    private String name;
    private Float weight;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
}
