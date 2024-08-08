package com.gear2go_frontend.view.component;

import com.gear2go_frontend.domain.DateRange;
import com.gear2go_frontend.factory.UiViewFactory;
import com.gear2go_frontend.service.CartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartViewComponent extends HorizontalLayout {

    private final H1 titleHeader = new H1("Cart");
    private final H2 summaryHeader = new H2("Summary");
    @Getter
    private final DatePicker rentDate = new DatePicker("Rent date");
    @Getter
    private final DatePicker returnDate = new DatePicker("Return date");
    private final BigDecimalField totalPrice = new BigDecimalField("Total");
    @Getter
    private final Button checkoutBtn = new Button("Checkout");
    private final VerticalLayout cartItemList = new VerticalLayout();
    private final VerticalLayout summary = new VerticalLayout();
    private final CartService cartService;
    private final UiViewFactory uiViewFactory;


    public void initialize() {
        HorizontalLayout datePickerContainer = new HorizontalLayout(rentDate, returnDate);


        totalPrice.setReadOnly(true);
        totalPrice.setSuffixComponent(new Div("PLN"));

        checkoutBtn.setWidth("100%");
        cartItemList.add(titleHeader);
        cartItemList.setMinWidth("300px");
        cartItemList.setMaxWidth("1000px");
        cartItemList.addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderRadius.LARGE, LumoUtility.Margin.Right.LARGE);

        refresh();

        summary.add(summaryHeader, datePickerContainer, totalPrice, checkoutBtn);
        summary.setMaxWidth("500px");
        summary.addClassNames(LumoUtility.FlexWrap.WRAP, LumoUtility.Background.CONTRAST_5, LumoUtility.Border.ALL, LumoUtility.BorderRadius.LARGE);
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexWrap.WRAP, LumoUtility.JustifyContent.CENTER, LumoUtility.Margin.Top.MEDIUM);
        setFlexGrow(3, cartItemList);
        setFlexGrow(1, summary);
        add(cartItemList, summary);

        rentDate.addValueChangeListener(e -> {
            returnDate.setMin(e.getValue());
            cartService.setDateRange(new DateRange(rentDate.getValue(), returnDate.getValue(), null), success -> {
            }, error -> {
            });
            refresh();
        });
        returnDate.addValueChangeListener(e -> {
            rentDate.setMax(e.getValue());
            cartService.setDateRange(new DateRange(rentDate.getValue(), returnDate.getValue(), null), success -> {
            }, error -> {
            });
            refresh();
        });

    }

    public void refresh() {
        cartService.getCart(
                cart -> {
                    cartItemList.removeAll();
                    totalPrice.setValue(cart.totalPrice());
                    cart.cartItemResponseList().forEach(cartItem -> cartItemList.add(uiViewFactory.createCartItemCard(cartItem, this)));
                    rentDate.setValue(cart.rentDate());
                    returnDate.setValue(cart.returnDate());
                },
                error -> {
                });
    }

}
