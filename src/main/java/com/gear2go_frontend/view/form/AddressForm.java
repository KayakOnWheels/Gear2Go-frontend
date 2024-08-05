package com.gear2go_frontend.view.form;

import com.gear2go_frontend.domain.Address;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;


public class AddressForm extends Div {

    private final Binder<Address> binder = new Binder<>(Address.class);
    @Getter
    Button save = new Button("Save");

    public AddressForm() {
        TextField street = new TextField("Street");
        TextField buildingNumber = new TextField("Building Number");
        TextField apartmentNumber = new TextField("Apartment");
        TextField postalCode = new TextField("Postal Code");
        TextField city = new TextField("City");


        FormLayout formLayout = new FormLayout();
        formLayout.add(street, buildingNumber, apartmentNumber, postalCode,
                city, save);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),// Use one column by default
                new ResponsiveStep("500px", 2));// Use two columns, if layout's width exceeds 500px
        formLayout.setColspan(street, 2);

        formLayout.setColspan(save, 2);

        add(formLayout);
        binder.bind(street, "street");
        binder.bind(buildingNumber, "buildingNumber");
        binder.bind(apartmentNumber, "apartmentNumber");
        binder.bind(postalCode, "postalCode");
        binder.bind(city, "city");

    }
}