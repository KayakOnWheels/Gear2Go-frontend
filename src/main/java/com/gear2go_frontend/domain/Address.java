package com.gear2go_frontend.domain;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
}
