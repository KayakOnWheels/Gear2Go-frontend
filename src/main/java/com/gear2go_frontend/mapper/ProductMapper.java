package com.gear2go_frontend.mapper;

import com.gear2go_frontend.dto.ProductResponse;
import com.gear2go_frontend.dto.UpdateCreateProductRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductResponse mapToProductResponse(UpdateCreateProductRequest updateCreateProductRequest) {
        return new ProductResponse(
                updateCreateProductRequest.id(),
                updateCreateProductRequest.name(),
                updateCreateProductRequest.weight(),
                updateCreateProductRequest.price(),
                updateCreateProductRequest.stock(),
                updateCreateProductRequest.imageUrl()
        );
    }
    public UpdateCreateProductRequest mapToUpdateCreateRequest(ProductResponse productResponse) {
        return new UpdateCreateProductRequest(
                productResponse.id(),
                productResponse.name(),
                productResponse.weight(),
                productResponse.price(),
                productResponse.stock(),
                productResponse.imageUrl()
        );
    }

}
