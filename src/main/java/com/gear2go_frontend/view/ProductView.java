package com.gear2go_frontend.view;


import com.gear2go_frontend.domain.Product;
import com.gear2go_frontend.service.ProductService;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Route(value = "product", layout = Layout.class)
@PageTitle("Product Gallery")
public class ProductView extends Main implements HasComponents, HasStyle {

    private HorizontalLayout imageContainer;
    private final ExceptionNotification exceptionNotification;
    private final ProductService productService;

    public ProductView(ProductService productService, ExceptionNotification exceptionNotification) {
        this.productService = productService;
        this.exceptionNotification = exceptionNotification;

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
                JustifyContent.START,
                Margin.Top.MEDIUM
        );

        container.add(headerContainer, sortBy);
        add(container, imageContainer);

        try {
            List<Product> productList = productService.getProductList();
            productList.forEach(product -> {
                imageContainer.add(new ImageGalleryViewCard(product.getName(), product.getImageUrl(), product.getPrice()));
            });
        } catch (Exception e) {
            exceptionNotification.showErrorNotification(e.getMessage());
        }


        Button button = new Button("Add to cart");
        button.addClickListener(clickEvent -> {

        });
    }
}
