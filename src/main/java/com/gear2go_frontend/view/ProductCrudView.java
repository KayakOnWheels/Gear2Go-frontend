package com.gear2go_frontend.view;

import com.gear2go_frontend.ProductMapper;
import com.gear2go_frontend.dto.ProductResponse;
import com.gear2go_frontend.dto.UpdateCreateProductRequest;
import com.gear2go_frontend.service.ProductService;
import com.gear2go_frontend.view.component.ExceptionNotification;
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
    private final ExceptionNotification exceptionNotification;
    private Grid<ProductResponse> grid = new Grid<>(ProductResponse.class);
    private TextField filter = new TextField();
    private ProductForm productForm;
    private Button addNewProduct = new Button("Add new Product");
    private final ProductMapper productMapper;

    @Autowired
    public ProductCrudView(ProductService productService, ExceptionNotification exceptionNotification, ProductMapper productMapper) {
        this.productService = productService;
        this.exceptionNotification = exceptionNotification;
        this.productForm = new ProductForm(productService, exceptionNotification, this);
        this.productMapper = productMapper;
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
                success -> grid.setItems(success),
                error -> exceptionNotification.showErrorNotification(error.getMessage()));
    }

    private void update() {
        productService.findProductsByName(filter.getValue(),
                success -> grid.setItems(success),
                error -> exceptionNotification.showErrorNotification(error.getMessage()));
    }
}
