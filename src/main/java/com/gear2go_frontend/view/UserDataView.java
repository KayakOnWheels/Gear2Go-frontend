package com.gear2go_frontend.view;

import com.gear2go_frontend.view.form.AddressForm;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("user-data")
public class UserDataView extends Div {


    Accordion accordion = new Accordion();

    public UserDataView() {
        setSizeFull();
        addClassNames(LumoUtility.JustifyContent.CENTER, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.Background.CONTRAST_5);

        accordion.setMaxWidth("800px");
        AccordionPanel personalDetails = new AccordionPanel("PersonalDetails", new AddressForm());
        AccordionPanel billingAddress = new AccordionPanel("Billing Address", new AddressForm());
        AccordionPanel shippingAddress = new AccordionPanel("Shipping Address", new AddressForm());
        accordion.add(personalDetails);
        accordion.add(billingAddress);
        accordion.add(shippingAddress);
        add(accordion);

    }
}