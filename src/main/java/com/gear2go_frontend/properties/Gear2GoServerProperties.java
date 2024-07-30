package com.gear2go_frontend.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("gear2go-server.api")
public class Gear2GoServerProperties {

    private String endpoint;
    private String product;
    private String user;
}
