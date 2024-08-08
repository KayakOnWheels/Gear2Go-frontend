package com.gear2go_frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.page.WebStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UriService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public URI buildUri(String url, MultiValueMap<String, String> parameters) {

        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(parameters)
                .build()
                .encode()
                .toUri();
    }

    public URI buildUri(String url) {

        return UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .encode()
                .toUri();
    }

    public <T> void requestSecuredEndpoint(String endpoint, HttpMethod httpMethod, Object request, ParameterizedTypeReference<T> responseType, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        URI uri = buildUri(endpoint);

        WebStorage.getItem(WebStorage.Storage.LOCAL_STORAGE, "jwtToken", token -> {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Object> entity = new HttpEntity<>(request, headers);
            try {
                ResponseEntity<T> response = restTemplate.exchange(uri, httpMethod, entity, responseType);
                T body = response.getBody();
                onSuccess.accept(body);
            } catch (Exception e) {
                onError.accept(e);
            }
        });
    }

    public <T> void requestEndpoint(String endpoint, HttpMethod httpMethod, Object request, ParameterizedTypeReference<T> responseType, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        URI uri = buildUri(endpoint);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Object> entity = new HttpEntity<>(request, headers);
            try {
                ResponseEntity<T> response = restTemplate.exchange(uri, httpMethod, entity, responseType);
                T body = response.getBody();

                onSuccess.accept(body);
            } catch (Exception e) {
                onError.accept(e);
            }
    }

}
