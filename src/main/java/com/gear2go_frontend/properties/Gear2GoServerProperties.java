package com.gear2go_frontend.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("gear2go-server.api.root")
public class Gear2GoServerProperties {

    private String root;
    private String product;
    private String user;
    private String cart;
    private String address;

    private String productAdmin;
    private String userAdmin;
    private String cartAdmin;
    private String addressAdmin;

    private String authenticate;
    private String authenticateGuest;
    private String registerUser;
    private String recoverPassword;


    private String tokenValidation;
    private String productCrud;
    private String productAvailability;
    private String rentDates;
}
