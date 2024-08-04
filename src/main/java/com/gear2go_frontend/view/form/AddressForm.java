package com.gear2go_frontend.view.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;


public class AddressForm extends Div {

    public AddressForm() {
        TextField street = new TextField("Street");
        TextField buildingNumber = new TextField("Building Number");
        TextField apartment = new TextField("Apartment");
        TextField postalCode = new TextField("Postal Code");
        TextField city = new TextField("City");
        Button save = new Button("Save");

        FormLayout formLayout = new FormLayout();
        formLayout.add(street, buildingNumber, apartment, postalCode,
                city, save);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),// Use one column by default
                new ResponsiveStep("500px", 2));// Use two columns, if layout's width exceeds 500px
        formLayout.setColspan(street, 2);

        formLayout.setColspan(save, 2);

        add(formLayout);
    }
}