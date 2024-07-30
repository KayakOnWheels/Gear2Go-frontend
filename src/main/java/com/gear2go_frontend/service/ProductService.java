package com.gear2go_frontend.service;

import com.gear2go_frontend.domain.Product;
import com.gear2go_frontend.domain.User;
import com.gear2go_frontend.properties.Gear2GoServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;
    private final UriService uriService;
    private final Gear2GoServerProperties gear2GoServerProperties;


    public List<Product> getProductList() throws Exception {
        URI uri = uriService.buildUri(gear2GoServerProperties.getProduct());
        return getProducts(uri);
    }

    public List<Product> findProductsByName(String name) throws Exception {
        URI uri = uriService.buildUri(gear2GoServerProperties.getProduct() + name);
        return getProducts(uri);
    }

    public void updateProduct(Product productRequest, Consumer<List<User>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getProduct(), HttpMethod.PUT, productRequest, new ParameterizedTypeReference<List<User>>() {},
                onSuccess,
                onError
        );
    }

    public void addProduct(Product productRequest, Consumer<List<User>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getProduct(), HttpMethod.POST, productRequest, new ParameterizedTypeReference<List<User>>() {},
                onSuccess,
                onError
        );
    }

    public void deleteProduct(Product productRequest, Consumer<List<User>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getProduct(), HttpMethod.DELETE, productRequest, new ParameterizedTypeReference<List<User>>() {},
                onSuccess,
                onError
        );
    }


    private List<Product> getProducts(URI uri) throws Exception {
        ResponseEntity<List<Product>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception(String.valueOf(response.getBody()));
        }

        return response.getBody() != null ? response.getBody() : Collections.emptyList();
    }


}
