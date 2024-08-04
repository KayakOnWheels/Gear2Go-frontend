package com.gear2go_frontend.view.component;

import com.gear2go_frontend.domain.CartItem;
import com.gear2go_frontend.domain.DateRange;
import com.gear2go_frontend.dto.ProductAvailabilityRequest;
import com.gear2go_frontend.dto.ProductResponse;
import com.gear2go_frontend.service.CartService;
import com.gear2go_frontend.service.ProductService;
import com.gear2go_frontend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;


public class ProductCard extends Div {


    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    Span priceTag = new Span("0");
    Span availabilityTag = new Span();
    private ProductResponse product;
    private String imageUrl;
    private LocalDate rentDate = LocalDate.now();
    private LocalDate returnDate = LocalDate.now().plusDays(1);

    public ProductCard(ProductResponse product, CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
        this.product = product;

        try {
            new URL(product.imageUrl());
            imageUrl = product.imageUrl();
        } catch (Exception e) {
            imageUrl = "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80";
        }

        addClassNames(
                "image-gallery-view-card",
                LumoUtility.Background.CONTRAST_5,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Padding.SMALL);

        setWidth("300px");

        Image image = new Image(imageUrl, product.name());
        image.setWidth("100%");
        image.addClassNames(LumoUtility.BorderRadius.SMALL);

        Paragraph titleParagraph = new Paragraph(product.name());

        priceTag.getElement().getThemeList().add("badge contrast");
        availabilityTag.getElement().getThemeList().add("pending");

        titleParagraph.addClassNames(
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.FontWeight.MEDIUM,
                LumoUtility.Margin.Top.SMALL,
                LumoUtility.Margin.Bottom.NONE);


        priceTag.addClassNames(
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.FontWeight.MEDIUM,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.Margin.Bottom.NONE);

        availabilityTag.addClassNames(
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.FontWeight.MEDIUM,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.Margin.Bottom.NONE);


        Button button = new Button("Add to cart");
        AtomicInteger retryCounter = new AtomicInteger(0);

        button.addClickListener(clickEvent -> {
            WebStorage.getItem("jwtToken",
                    token -> {
                        cartService.addCartItem(
                                new CartItem(product.id(), 1, BigDecimal.ZERO),
                                cart -> {
                                    retryCounter.set(0);

                                    cartService.setDateRange(new DateRange(rentDate, returnDate, null),
                                            success -> {
                                            },
                                            error -> {
                                            });
                                    Notification notification = Notification
                                            .show("Added to cart");
                                    notification.setDuration(2000);
                                    notification.setPosition(Notification.Position.MIDDLE);
                                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                                    int newAvailability = Pattern.compile("\\d+")
                                            .matcher(availabilityTag.getText())
                                            .results()
                                            .map(MatchResult::group)
                                            .mapToInt(Integer::parseInt)
                                            .findFirst()
                                            .orElse(-1);
                                    if (newAvailability != -1) {
                                        availabilityTag.setText(String.format("Availability: %s", newAvailability - 1));
                                    }

                                },
                                error -> {
                                    if (error.getMessage().contains("403") && retryCounter.get() < 5) {
                                        retryCounter.getAndIncrement();
                                        userService.getGuestAuthenticationToken();
                                        button.click();
                                    }
                                    Notification notification = Notification
                                            .show("Error");
                                    notification.setDuration(2000);
                                    notification.setPosition(Notification.Position.MIDDLE);
                                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                                }
                        );
                    });
        });

        VerticalLayout tagContainer = new VerticalLayout(priceTag, availabilityTag);
        add(image, titleParagraph, tagContainer, button);
    }

    public void updatePrices() {
        priceTag.setText(String.format("$%s", product.price().multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(rentDate, returnDate)))));
    }

    public void updateAvailability() {
        productService.getProductAvailability(new ProductAvailabilityRequest(product.id(), rentDate, returnDate),
                success -> availabilityTag.setText(String.format("Availability: %s", success)),
                error -> {
                });
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}