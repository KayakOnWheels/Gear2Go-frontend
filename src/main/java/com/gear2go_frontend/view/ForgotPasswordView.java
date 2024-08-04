package com.gear2go_frontend.view;

import com.gear2go_frontend.dto.SendRecoveryMailRequest;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.view.component.Notification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "forgot-password", layout = Layout.class)
public class ForgotPasswordView extends Div {

    private final EmailField emailField = new EmailField("Email");
    private final UserService userService;
    private final Notification notification;


    public ForgotPasswordView(UserService userService, Notification notification) {
        this.userService = userService;
        this.notification = notification;
        setSizeFull();
        addClassNames(LumoUtility.JustifyContent.CENTER, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.Background.CONTRAST_5);
        VerticalLayout form = new VerticalLayout();
        form.setMaxWidth("400px");
        form.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        form.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        form.addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Background.BASE);
        H1 header = new H1("Get password recovery mail");


        emailField.setWidth("300px");


        Button submitButton = new Button("Send recovery email",
                event -> userService.sendPasswordRecoveryMail(new SendRecoveryMailRequest(emailField.getValue()),
                        success -> notification.showSuccessNotification("Email sent"),
                        error -> notification.showErrorNotification(error.getMessage())));

        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        form.add(header, emailField, submitButton);
        add(form);
    }

}