package com.gear2go_frontend.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class UriService {

    public URI buildUri(String url, MultiValueMap<String, String> parameters) {

        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(parameters)
                .build()
                .encode()
                .toUri();
    }
}
