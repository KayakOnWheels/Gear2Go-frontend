package com.gear2go_frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ProductResponse {
    private Long id;
    private String name;
    private Float weight;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;


}
