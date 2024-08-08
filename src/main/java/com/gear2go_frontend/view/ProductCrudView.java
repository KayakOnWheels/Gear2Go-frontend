package com.gear2go_frontend.view;

import com.gear2go_frontend.factory.UiViewFactory;
import com.gear2go_frontend.mapper.ProductMapper;
import com.gear2go_frontend.dto.ProductResponse;
import com.gear2go_frontend.dto.UpdateCreateProductRequest;
import com.gear2go_frontend.service.ProductService;
import com.gear2go_frontend.view.component.CustomNotification;
import com.gear2go_frontend.view.form.ProductForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "product-crud", layout = Layout.class)
public class ProductCrudView extends VerticalLayout {

    private final ProductService productService;
    private final CustomNotification customNotification;
    private final Grid<ProductResponse> grid = new Grid<>(ProductResponse.class);
    private final TextField filter = new TextField();
    private final ProductForm productForm;
    private final ProductMapper productMapper;
    private final UiViewFactory uiViewFactory;


    public ProductCrudView(ProductService productService, CustomNotification customNotification, ProductMapper productMapper, UiViewFactory uiViewFactory) {
        this.productService = productService;
        this.customNotification = customNotification;
        this.productMapper = productMapper;
        this.uiViewFactory = uiViewFactory;
        this.productForm = uiViewFactory.createProductForm(this);

        filter.setPlaceholder("Filter by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());

        grid.setColumns("id", "name", "weight", "price", "stock");
        HorizontalLayout mainContent = new HorizontalLayout(grid, productForm);
        mainContent.setSizeFull();
        grid.setSizeFull();
        productForm.setProductFormVisibility(null);

        grid.asSingleSelect().addValueChangeListener(e -> {
            productForm.getSaveBtn().setVisible(true);
            productForm.getDeleteBtn().setVisible(true);
            productForm.getAddNewBtn().setVisible(false);
            productForm.setProductFormVisibility(productMapper.mapToUpdateCreateRequest(grid.asSingleSelect().getValue()));
        });


        Button addNewProduct = new Button("Add new Product");
        addNewProduct.addClickListener(e -> {
            grid.asSingleSelect().clear();
            productForm.getSaveBtn().setVisible(false);
            productForm.getDeleteBtn().setVisible(false);
            productForm.getAddNewBtn().setVisible(true);
            productForm.setProductFormVisibility(new UpdateCreateProductRequest());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewProduct);

        add(toolbar, mainContent);
        setSizeFull();
        refresh();
    }

    public void refresh() {

        productService.getProductList(
                grid::setItems,
                error -> customNotification.showErrorNotification(error.getMessage()));
    }

    private void update() {
        productService.findProductsByName(filter.getValue(),
                grid::setItems,
                error -> customNotification.showErrorNotification(error.getMessage()));
    }
}
