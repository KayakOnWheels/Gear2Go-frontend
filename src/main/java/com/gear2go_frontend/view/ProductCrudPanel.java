package com.gear2go_frontend.view;

import com.gear2go_frontend.dto.Product;
import com.gear2go_frontend.service.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "products-crud", layout = Layout.class)
public class ProductCrudPanel extends VerticalLayout {

    private ProductService productService;
    private Grid<Product> grid = new Grid<>(Product.class);
    private TextField filter = new TextField();
    private ProductForm productForm;
    private Button addNewBook = new Button("Add new Product");

    @Autowired
    public ProductCrudPanel(ProductService productService) {
        this.productService = productService;
        this.productForm = new ProductForm(productService, this);

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
            productForm.setProductFormVisibility(grid.asSingleSelect().getValue());
        });


        addNewBook.addClickListener(e -> {
            grid.asSingleSelect().clear();
            productForm.getSaveBtn().setVisible(false);
            productForm.getDeleteBtn().setVisible(false);
            productForm.getAddNewBtn().setVisible(true);
            productForm.setProductFormVisibility(new Product());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewBook);

        add(toolbar, mainContent);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        grid.setItems(productService.getProductList());
    }

    private void update() {
        grid.setItems(productService.findProductsByName(filter.getValue()));
    }
}
