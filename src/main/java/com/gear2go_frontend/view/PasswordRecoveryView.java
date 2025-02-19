package com.gear2go_frontend.view;

import com.gear2go_frontend.dto.RecoverPasswordRequest;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.view.component.CustomNotification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Arrays;

@Route(value = "password-recovery", layout = Layout.class)
public class PasswordRecoveryView extends Div implements HasUrlParameter<String> {

    private String token;
    private final UserService userService;
    private final CustomNotification customNotification;


    public PasswordRecoveryView(UserService userService, CustomNotification customNotification) {
        this.userService = userService;
        this.customNotification = customNotification;

        setSizeFull();
        addClassNames(LumoUtility.JustifyContent.CENTER, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.Background.CONTRAST_5);
        VerticalLayout form = new VerticalLayout();
        form.setMaxWidth("400px");
        form.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        form.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        form.addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Background.BASE);
        H1 header = new H1("Change Password");


        PasswordField newPasswordField = new PasswordField("New Password");
        newPasswordField.setWidth("300px");

        PasswordField repeatNewPasswordField = new PasswordField("Repeat New Password");
        repeatNewPasswordField.setWidth("300px");


        Button submitButton = new Button("Reset password", e -> {
            String newPassword = newPasswordField.getValue();
            String repeatNewPassword = repeatNewPasswordField.getValue();
            if (!newPassword.equals(repeatNewPassword)) {
                Notification.show("Passwords do not match");
                return;
            } else {
                Notification.show("Password changed successfully");
            }

            userService.recoverPassword(new RecoverPasswordRequest(token, Arrays.toString(newPassword.getBytes())),
                    success -> customNotification.showSuccessNotification("You can login with new password now"),
                    error -> customNotification.showErrorNotification(error.getMessage()));
        });

        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        form.add(header, newPasswordField, repeatNewPasswordField, submitButton);
        add(form);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        token = parameter;
    }

}