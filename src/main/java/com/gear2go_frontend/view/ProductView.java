package com.gear2go_frontend.view;


import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Route(value = "products", layout = Layout.class)
@PageTitle("Product Gallery")
public class Products extends Main implements HasComponents, HasStyle {

    private HorizontalLayout imageContainer;

    public Products() {
        constructUI();

        imageContainer.add(new ImageGalleryViewCard("Snow mountains under stars",
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new ImageGalleryViewCard("Snow covered mountain",
                "https://images.unsplash.com/photo-1512273222628-4daea6e55abb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new ImageGalleryViewCard("River between mountains",
                "https://images.unsplash.com/photo-1536048810607-3dc7f86981cb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=375&q=80"));
        imageContainer.add(new ImageGalleryViewCard("Milky way on mountains",
                "https://images.unsplash.com/photo-1515705576963-95cad62945b6?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=750&q=80"));
        imageContainer.add(new ImageGalleryViewCard("Mountain with fog",
                "https://images.unsplash.com/photo-1513147122760-ad1d5bf68cdb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80"));
        imageContainer.add(new ImageGalleryViewCard("Mountain at night",
                "https://images.unsplash.com/photo-1562832135-14a35d25edef?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=815&q=80"));

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
                AlignItems.CENTER,
                JustifyContent.BETWEEN,
                FlexWrap.WRAP,
                AlignItems.END
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
        sortBy.setItems("Popularity", "Newest first", "Oldest first");
        sortBy.setValue("Popularity");
        sortBy.addClassNames(
                Margin.Bottom.LARGE
        );



        imageContainer = new HorizontalLayout();
        imageContainer.addClassNames(
                Gap.MEDIUM,
                Display.FLEX,
                FlexWrap.WRAP,
                JustifyContent.CENTER,
                Margin.Top.MEDIUM
        );

        container.add(headerContainer, sortBy);
        add(container, imageContainer);
    }

    private void addImageCard(String title, String imageUrl) {
        ImageGalleryViewCard card = new ImageGalleryViewCard(title, imageUrl);
        imageContainer.add(card);
    }
}
