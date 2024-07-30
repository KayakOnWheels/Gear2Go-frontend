package com.gear2go_frontend.service;

import com.gear2go_frontend.domain.Product;
import com.gear2go_frontend.domain.User;
import com.gear2go_frontend.properties.Gear2GoServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;
    private final UriService uriService;
    private final Gear2GoServerProperties gear2GoServerProperties;


    public List<Product> getProductList() throws Exception {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product");

        return getProducts(uri);
    }

    public List<Product> findProductsByName(String name) throws Exception {
        URI uri = uriService.buildUri(gear2GoServerProperties.getProduct() + name);
        return getProducts(uri);
    }

    public void updateProduct(Product product) throws Exception {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product");
        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.PUT, new org.springframework.http.HttpEntity<>(product), Void.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.valueOf(response.getBody()));
        }
    }

    public void addProduct(Product product) throws Exception {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product");
        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.POST, new org.springframework.http.HttpEntity<>(product), Void.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new Exception(String.valueOf(response.getBody()));
        }
    }

    public void deleteProduct(Long id) throws Exception {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product/" + id);
        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.valueOf(response.getBody()));
        }
    }


    private List<Product> getProducts(URI uri) throws Exception {
        ResponseEntity<List<Product>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.valueOf(response.getBody()));
        }

        return response.getBody() != null ? response.getBody() : Collections.emptyList();
    }


}
