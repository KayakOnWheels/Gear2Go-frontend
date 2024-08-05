package com.gear2go_frontend.view;

import com.gear2go_frontend.factory.UiViewFactory;
import com.gear2go_frontend.view.component.CartViewComponent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route(value = "cart", layout = Layout.class)
public class CartView extends Div {

    private final UiViewFactory uiViewFactory;
    private CartViewComponent cartViewComponent;

    public CartView(UiViewFactory uiViewFactory) {
        this.uiViewFactory = uiViewFactory;

        cartViewComponent = uiViewFactory.createCartViewComponent();
        cartViewComponent.getCheckoutBtn().addClickListener(event -> getUI().ifPresent(ui -> ui.navigate(FinaliseOrder.class)));
        add(cartViewComponent);
    }
}
