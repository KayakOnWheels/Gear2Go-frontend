package com.gear2go_frontend;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ImageGalleryViewCard extends Div {
    public ImageGalleryViewCard(String title, String imageUrl) {
        addClassNames(
                "image-gallery-view-card",
                LumoUtility.Background.CONTRAST_5,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Padding.SMALL);

        setWidth("300px");  // Set a fixed width for each card

        Image image = new Image(imageUrl, title);
        image.setWidth("100%");
        image.addClassNames(LumoUtility.BorderRadius.SMALL);

        Paragraph titleParagraph = new Paragraph(title);

        titleParagraph.addClassNames(
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.FontWeight.MEDIUM,
                LumoUtility.Margin.Top.SMALL,
                LumoUtility.Margin.Bottom.NONE);

        add(image, titleParagraph);
    }
}