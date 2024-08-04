package com.gear2go_frontend.service;

import com.gear2go_frontend.domain.Product;
import com.gear2go_frontend.dto.ProductAvailabilityRequest;
import com.gear2go_frontend.dto.ProductResponse;
import com.gear2go_frontend.dto.UpdateCreateProductRequest;
import com.gear2go_frontend.properties.Gear2GoServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final UriService uriService;
    private final Gear2GoServerProperties gear2GoServerProperties;

    public void getProductList(Consumer<List<ProductResponse>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestEndpoint(gear2GoServerProperties.getProduct(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        }, onSuccess, onError);
    }

    public void findProductsByName(String name, Consumer<List<ProductResponse>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestEndpoint(gear2GoServerProperties.getProduct() + name, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        }, onSuccess, onError);
    }

    public void getProductById(Long id, Consumer<ProductResponse> onSuccess, Consumer<Throwable> onError) {
        uriService.requestEndpoint(gear2GoServerProperties.getProduct() + "/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        }, onSuccess, onError);
    }

    public void getProductAvailability(ProductAvailabilityRequest productAvailabilityRequest, Consumer<Integer> onSuccess, Consumer<Throwable> onError) {
        uriService.requestEndpoint(gear2GoServerProperties.getProductAvailability(), HttpMethod.POST, productAvailabilityRequest, new ParameterizedTypeReference<>() {
        }, onSuccess, onError);
    }

    public void updateProduct(UpdateCreateProductRequest updateCreateProductRequest, Consumer<List<ProductResponse>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getProductCrud(), HttpMethod.PUT, updateCreateProductRequest, new ParameterizedTypeReference<>() {
                },
                onSuccess,
                onError
        );
    }

    public void addProduct(UpdateCreateProductRequest updateCreateProductRequest, Consumer<List<ProductResponse>> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getProductCrud(), HttpMethod.POST, updateCreateProductRequest, new ParameterizedTypeReference<>() {
                },
                onSuccess,
                onError
        );
    }

    public void deleteProduct(Long id, Consumer<Void> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getProductCrud() + "/crud/" + id, HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                },
                onSuccess,
                onError
        );
    }


}
