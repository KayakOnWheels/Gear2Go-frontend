package com.gear2go_frontend.view;

import com.gear2go_frontend.dto.Product;
import com.gear2go_frontend.service.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

public class ProductForm extends FormLayout {

    private TextField id = new TextField("Id");
    private TextField name = new TextField("Name");
    private NumberField weight = new NumberField("Weight");
    private NumberField price = new NumberField("Price");
    private NumberField stock = new NumberField("Stock");
    private ProductService productService;

    @Getter
    private Button saveBtn = new Button("Save");
    @Getter
    private Button deleteBtn = new Button("Delete");
    @Getter
    private Button addNewBtn = new Button("Add");
    private Binder<Product> binder = new Binder<Product>(Product.class);
    private ProductCrudPanel productCrudPanel;

    public ProductForm(ProductService productService, ProductCrudPanel productCrudPanel) {
        this.productCrudPanel = productCrudPanel;
        this.productService = productService;

        HorizontalLayout buttons = new HorizontalLayout(saveBtn, deleteBtn, addNewBtn);

        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(id, name, weight, price, stock, buttons);

        binder.bindInstanceFields(this);

        saveBtn.addClickListener(event -> save());
        addNewBtn.addClickListener(event -> addNew());
        deleteBtn.addClickListener(event -> delete());

        addNewBtn.setVisible(false);

        id.setReadOnly(true);
        name.setReadOnly(false);
        weight.setReadOnly(false);
        price.setReadOnly(false);
        stock.setReadOnly(false);
    }

    protected void save() {
        Product product = binder.getBean();
        productService.updateProduct(product);
        productCrudPanel.refresh();
        setProductFormVisibility(null);
    }

    protected void addNew() {
        Product product = binder.getBean();
        productService.addProduct(product);
        productCrudPanel.refresh();
        setProductFormVisibility(null);
    }

    protected void delete() {
        Product product = binder.getBean();
        productService.deleteProduct(product.getId());
        productCrudPanel.refresh();
        setProductFormVisibility(null);
    }

    public void setProductFormVisibility(Product product) {
        binder.setBean(product);

        if (product == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }
}
