package com.gear2go_frontend.dto;

import lombok.Data;

@Data
public class Product {

    private Long id;
    private String name;
    private Double weight;
    private Double price;
    private Double stock;

}