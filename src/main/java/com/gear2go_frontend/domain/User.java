package com.gear2go_frontend.domain;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private String accessLevel;
}
