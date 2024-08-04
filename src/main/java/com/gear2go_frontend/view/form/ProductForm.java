package com.gear2go_frontend.view.form;

import com.gear2go_frontend.dto.UpdateCreateProductRequest;
import com.gear2go_frontend.service.ProductService;
import com.gear2go_frontend.view.component.Notification;
import com.gear2go_frontend.view.ProductCrudView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

public class ProductForm extends FormLayout {

    private final TextField id = new TextField("Id");
    private final TextField name = new TextField("Name");
    private final NumberField weight = new NumberField("Weight");
    private final BigDecimalField price = new BigDecimalField("Price");
    private final IntegerField stock = new IntegerField("Stock");
    private final ProductService productService;
    private final Notification notification;
    @Getter
    private final Button saveBtn = new Button("Save");
    @Getter
    private final Button deleteBtn = new Button("Delete");
    @Getter
    private final Button addNewBtn = new Button("Add");

    private final Binder<UpdateCreateProductRequest> binder = new Binder<>(UpdateCreateProductRequest.class);
    private final ProductCrudView productCrudView;

    public ProductForm(ProductService productService, Notification notification, ProductCrudView productCrudView) {
        this.productCrudView = productCrudView;
        this.productService = productService;
        this.notification = notification;

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
        productService.updateProduct(
                binder.getBean(),
                userList -> {
                    productCrudView.refresh();
                    setProductFormVisibility(null);
                },
                exception -> notification.showErrorNotification(exception.getMessage()));
    }

    protected void addNew() {
        productService.addProduct(
                binder.getBean(),
                userList -> {
                    productCrudView.refresh();
                    setProductFormVisibility(null);
                },
                exception -> notification.showErrorNotification(exception.getMessage()));
    }

    protected void delete() {
        productService.deleteProduct(
                Long.valueOf(id.getValue()),
                userList -> {
                    productCrudView.refresh();
                    setProductFormVisibility(null);
                },
                exception -> notification.showErrorNotification(exception.getMessage()));
    }

    public void setProductFormVisibility(UpdateCreateProductRequest product) {
        binder.setBean(product);

        if (product == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }
}
