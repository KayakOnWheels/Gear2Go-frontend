package com.gear2go_frontend.service;

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
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UriService {

    private final RestTemplate restTemplate;

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

    public <T> void getList(String endpoint, ParameterizedTypeReference<List<T>> responseType, Consumer<List<T>> onSuccess, Consumer<Throwable> onError) {
        URI uri = buildUri(endpoint);

        WebStorage.getItem(WebStorage.Storage.LOCAL_STORAGE, "jwtToken", token -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<List<T>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, responseType);
                List<T> body = response.getBody();
                List<T> list = body != null ? body : Collections.emptyList();
                onSuccess.accept(list);
            } catch (Exception e) {
                onError.accept(e);
            }
        });
    }
}
