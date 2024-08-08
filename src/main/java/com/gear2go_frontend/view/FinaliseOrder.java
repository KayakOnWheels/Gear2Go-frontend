package com.gear2go_frontend.view;

import com.gear2go_frontend.domain.Address;
import com.gear2go_frontend.factory.UiViewFactory;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.view.component.CartViewComponent;
import com.gear2go_frontend.view.form.AddressForm;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "order", layout = Layout.class)
public class FinaliseOrder extends VerticalLayout {

    private List<Address> addressList = new ArrayList<>();
    private final UiViewFactory uiViewFactory;
    private final UserService userService;

    public FinaliseOrder(UserService userService, UiViewFactory uiViewFactory) {
        this.userService = userService;
        this.uiViewFactory = uiViewFactory;

        CartViewComponent cartViewComponent = uiViewFactory.createCartViewComponent();
        cartViewComponent.getRentDate().setReadOnly(true);
        cartViewComponent.getReturnDate().setReadOnly(true);
        cartViewComponent.setWidth("100%");

        userService.getUserAddressList(
                success -> addressList = success,
                error -> {
                }
        );

        Select<Address> select1 = new Select<>();
        select1.setLabel("Your addresses");
        select1.setItems(addressList);

        Select<Address> select2 = new Select<>();
        select2.setLabel("Your addresses");
        select2.setItems(addressList);

        AddressForm shippingForm = new AddressForm();
        AddressForm billingForm = new AddressForm();


        VerticalLayout shippingAddress = new VerticalLayout(new H2("Shipping Address"), select1, shippingForm);
        VerticalLayout billingAddress = new VerticalLayout(new H2("Billing Address"), select2, billingForm);
        HorizontalLayout addresses = new HorizontalLayout(shippingAddress, billingAddress);
        add(addresses, new H2("Cart Review"), cartViewComponent);

        cartViewComponent.getCheckoutBtn().addClickListener(event -> {
        });
    }
}
