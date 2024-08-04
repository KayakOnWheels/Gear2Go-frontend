package com.gear2go_frontend.view;


import com.gear2go_frontend.domain.DateRange;
import com.gear2go_frontend.service.CartService;
import com.gear2go_frontend.service.ProductService;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.view.component.ExceptionNotification;
import com.gear2go_frontend.view.component.ProductCard;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Route(value = "product", layout = Layout.class)
@PageTitle("Product Gallery")
public class ProductGalleryView extends Main implements HasComponents, HasStyle {

    private HorizontalLayout imageContainer;
    private final ExceptionNotification exceptionNotification;
    private final ProductService productService;
    private final DatePicker rentDate = new DatePicker("Rent date");
    private final DatePicker returnDate = new DatePicker("Return date");
    private final CartService cartService;
    private final UserService userService;

    public ProductGalleryView(ProductService productService, ExceptionNotification exceptionNotification, CartService cartService, UserService userService) {
        this.productService = productService;
        this.exceptionNotification = exceptionNotification;
        this.cartService = cartService;
        this.userService = userService;

        constructUI();

    }

    private void constructUI() {
        addClassNames("image-gallery-view");
        addClassNames(
                MaxWidth.SCREEN_LARGE,
                Margin.Horizontal.AUTO,
                Padding.Bottom.LARGE,
                Padding.Horizontal.LARGE);


        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(
                AlignItems.END,
                JustifyContent.BETWEEN,
                FlexWrap.WRAP
        );


        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Products and Services");
        header.addClassNames(
                Margin.Bottom.NONE,
                Margin.Top.XLARGE,
                FontSize.XXXLARGE
        );
        headerContainer.getStyle().setMaxWidth("200px");


        Paragraph description = new Paragraph("Choose items you need");
        description.addClassNames(
                Margin.Bottom.SMALL,
                Margin.Top.NONE,
                TextColor.SECONDARY
        );
        headerContainer.add(header, description);


        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Price", "Availability");
        sortBy.setValue("Price");


        rentDate.setValue(LocalDate.now());
        returnDate.setValue(LocalDate.now().plusDays(1));

        rentDate.addValueChangeListener(e -> {
            returnDate.setMin(e.getValue());
            setDatesInProductCards();
        });

        returnDate.addValueChangeListener(e -> {
            rentDate.setMax(e.getValue());
            setDatesInProductCards();
        });

        HorizontalLayout filterContainer = new HorizontalLayout(rentDate, returnDate, sortBy);
        filterContainer.addClassNames(Padding.Bottom.LARGE, FlexWrap.WRAP);

        imageContainer = new HorizontalLayout();
        imageContainer.addClassNames(
                Gap.MEDIUM,
                Display.FLEX,
                FlexWrap.WRAP,
                JustifyContent.START,
                Margin.Top.MEDIUM
        );

        container.add(headerContainer, filterContainer);
        add(container, imageContainer);


        updatePricesAndAvailability();
    }

    private void setDatesInProductCards() {
        imageContainer.getChildren()
                .filter(element -> element instanceof ProductCard)
                .map(element -> (ProductCard) element)
                .forEach(card -> {
                    card.setRentDate(rentDate.getValue());
                    card.setReturnDate(returnDate.getValue());
                });
        cartService.setDateRange(new DateRange(rentDate.getValue(), returnDate.getValue(), null),
                success -> {
                },
                error -> {
                });

        updatePricesAndAvailability();
    }

    private void updatePricesAndAvailability() {

        productService.getProductList(
                success -> success.forEach(product -> imageContainer.add(new ProductCard(product, cartService, userService, productService))),
                error -> exceptionNotification.showErrorNotification(error.getMessage()));

        imageContainer.getChildren()
                .filter(element -> element instanceof ProductCard)
                .map(element -> (ProductCard) element)
                .forEach(ProductCard::updatePrices);

        imageContainer.getChildren()
                .filter(element -> element instanceof ProductCard)
                .map(element -> (ProductCard) element)
                .forEach(ProductCard::updateAvailability);
    }
}



