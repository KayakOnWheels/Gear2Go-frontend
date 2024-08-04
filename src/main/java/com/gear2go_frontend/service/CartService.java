package com.gear2go_frontend.service;

import com.gear2go_frontend.domain.CartItem;
import com.gear2go_frontend.domain.DateRange;
import com.gear2go_frontend.dto.CartResponse;
import com.gear2go_frontend.properties.Gear2GoServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UriService uriService;
    private final Gear2GoServerProperties gear2GoServerProperties;


    public void getCart(Consumer<CartResponse> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getCart(), HttpMethod.GET, null, new ParameterizedTypeReference<CartResponse>() {
                },
                onSuccess,
                onError
        );
    }

    public void addCartItem(CartItem cartItemRequest, Consumer<CartResponse> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getCart(), HttpMethod.PUT, cartItemRequest, new ParameterizedTypeReference<CartResponse>() {
                },
                onSuccess,
                onError
        );
    }

    public void setDateRange(DateRange dateRange, Consumer<CartResponse> onSuccess, Consumer<Throwable> onError) {
        uriService.requestSecuredEndpoint(gear2GoServerProperties.getRentDates(), HttpMethod.PUT, dateRange, new ParameterizedTypeReference<CartResponse>() {
                },
                onSuccess,
                onError
        );
    }
}
