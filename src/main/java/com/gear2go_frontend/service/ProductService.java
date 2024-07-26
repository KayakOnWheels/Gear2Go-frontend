package com.gear2go_frontend.service;

import com.gear2go_frontend.dto.Product;
import com.gear2go_frontend.properties.Gear2GoServerProperties;
import lombok.RequiredArgsConstructor;
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


    public List<Product> getProductList() {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product", null);

        Product[] productArray = restTemplate.getForObject(uri, Product[].class);
        return productArray != null ? Arrays.asList(productArray) : Collections.emptyList();
    }

    public List<Product> findProductsByName(String name) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product/" + name, null);

        Product[] productArray = restTemplate.getForObject(uri, Product[].class);
        return productArray != null ? Arrays.asList(productArray) : Collections.emptyList();
    }

    public void updateProduct(Product product) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product", null);
        restTemplate.put(uri, product);
    }

    public void addProduct(Product product) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product", null);
        restTemplate.postForEntity(uri, product, Void.class);
    }

    public void deleteProduct(Long id) {
        URI uri = uriService.buildUri(gear2GoServerProperties.getEndpoint() + "product/" + id, null);
        restTemplate.delete(uri);
    }

}
