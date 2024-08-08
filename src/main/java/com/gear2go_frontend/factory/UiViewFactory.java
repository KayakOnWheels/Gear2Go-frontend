package com.gear2go_frontend.factory;

import com.gear2go_frontend.domain.CartItem;
import com.gear2go_frontend.dto.ProductResponse;
import com.gear2go_frontend.service.CartService;
import com.gear2go_frontend.service.ProductService;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.view.ProductCrudView;
import com.gear2go_frontend.view.RegisterNewUserView;
import com.gear2go_frontend.view.UserCrudView;
import com.gear2go_frontend.view.component.CartItemCard;
import com.gear2go_frontend.view.component.CartViewComponent;
import com.gear2go_frontend.view.component.CustomNotification;
import com.gear2go_frontend.view.component.ProductCard;
import com.gear2go_frontend.view.form.ProductForm;
import com.gear2go_frontend.view.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UiViewFactory {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final CustomNotification customNotification;

    public CartViewComponent createCartViewComponent() {
        CartViewComponent component = new CartViewComponent(cartService, this);
        component.initialize();
        return component;
    }

    public CartItemCard createCartItemCard(CartItem cartItem, CartViewComponent cartViewComponent) {
        CartItemCard component = new CartItemCard(productService, cartService);
        component.initialize(cartItem, cartViewComponent);
        return component;
    }

    public UserForm createUserForm(UserCrudView userCrudView) {
        UserForm component = new UserForm(userService, customNotification);
        component.initialize(userCrudView);
        return component;
    }

    public ProductForm createProductForm(ProductCrudView productCrudView) {
        ProductForm component = new ProductForm(productService, customNotification);
        component.initialize(productCrudView);
        return component;
    }

    public ProductCard createProductCard(ProductResponse productResponse) {
        ProductCard component = new ProductCard(cartService, userService, productService);
        component.initialize(productResponse);
        return component;
    }

}
