package com.gear2go_frontend.factory;

import com.gear2go_frontend.domain.CartItem;
import com.gear2go_frontend.service.CartService;
import com.gear2go_frontend.service.ProductService;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.view.component.CartItemCard;
import com.gear2go_frontend.view.component.CartViewComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UiViewFactory {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;

    public CartViewComponent createCartViewComponent() {
        CartViewComponent component = new CartViewComponent(cartService, productService, this);
        component.initialize();
        return component;
    }

    public CartItemCard createCartItemCard(CartItem cartItem, CartViewComponent cartViewComponent) {
        CartItemCard component = new CartItemCard(productService, cartService);
        component.initialize(cartItem, cartViewComponent);
        return component;
    }
}
