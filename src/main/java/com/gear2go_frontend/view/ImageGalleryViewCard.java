package com.gear2go_frontend.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ImageGalleryViewCard extends Div {
    public ImageGalleryViewCard(String title, String imageUrl, Double price) {

        imageUrl = (imageUrl == null) ? "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80" : imageUrl;

        addClassNames(
                "image-gallery-view-card",
                LumoUtility.Background.CONTRAST_5,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Padding.SMALL);

        setWidth("300px");

        Image image = new Image(imageUrl, title);
        image.setWidth("100%");
        image.addClassNames(LumoUtility.BorderRadius.SMALL);

        Paragraph titleParagraph = new Paragraph(title);

        Span priceTag = new Span(String.format("$%s", price));
        priceTag.getElement().getThemeList().add("badge contrast");

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

        add(image, titleParagraph, priceTag);
    }
}