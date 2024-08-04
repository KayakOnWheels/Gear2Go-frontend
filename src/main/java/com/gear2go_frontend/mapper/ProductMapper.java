package com.gear2go_frontend.mapper;

import com.gear2go_frontend.dto.ProductResponse;
import com.gear2go_frontend.dto.UpdateCreateProductRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductResponse mapToProductResponse(UpdateCreateProductRequest updateCreateProductRequest) {
        return new ProductResponse(
                updateCreateProductRequest.getId(),
                updateCreateProductRequest.getName(),
                updateCreateProductRequest.getWeight(),
                updateCreateProductRequest.getPrice(),
                updateCreateProductRequest.getStock(),
                updateCreateProductRequest.getImageUrl()
        );
    }
    public UpdateCreateProductRequest mapToUpdateCreateRequest(ProductResponse productResponse) {
        return new UpdateCreateProductRequest(
                productResponse.getId(),
                productResponse.getName(),
                productResponse.getWeight(),
                productResponse.getPrice(),
                productResponse.getStock(),
                productResponse.getImageUrl()
        );
    }

}
