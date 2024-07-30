package com.gear2go_frontend.domain;

import lombok.Data;

@Data
public class Product {

    private Long id;
    private String name;
    private Double weight;
    private Double price;
    private Double stock;
    private String imageUrl;

}