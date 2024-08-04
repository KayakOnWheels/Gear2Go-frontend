package com.gear2go_frontend.view;

import com.gear2go_frontend.domain.User;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.view.component.Notification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("register")
public class RegisterNewUserView extends Div {


    private final EmailField mail = new EmailField("Email");
    private final PasswordField password = new PasswordField("Password");
    private final PasswordField repeatPasswordField = new PasswordField("Repeat Password");
    private final Binder<User> binder = new Binder<>(User.class);
    private final Notification notification;

    private final UserService userService;

    public RegisterNewUserView(UserService userService, Notification notification) {
        this.userService = userService;
        this.notification = notification;

        setSizeFull();
        addClassNames(LumoUtility.JustifyContent.CENTER, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.Background.CONTRAST_5);
        VerticalLayout form = new VerticalLayout();
        form.setMaxWidth("400px");
        form.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        form.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        form.addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Background.BASE);
        H1 header = new H1("Create Account");

        mail.setWidth("300px");
        password.setWidth("300px");
        repeatPasswordField.setWidth("300px");
        binder.setBean(new User());
        binder.bind(mail, User::getMail, User::setMail);
        binder.bind(password, User::getPassword, User::setPassword);

        Button submitButton = new Button("Submit", e -> {
            String password = this.password.getValue();
            String repeatPassword = repeatPasswordField.getValue();

            if (!password.equals(repeatPassword)) {
                com.vaadin.flow.component.notification.Notification.show("Passwords do not match");
                return;
            }
            userService.addUser(binder.getBean(),
                    success -> getUI().ifPresent(ui -> ui.navigate(ProductGalleryView.class)),
                    error -> notification.showErrorNotification("Something went wrong"));
        });

        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        form.add(header, mail, password, repeatPasswordField, submitButton);
        add(form);
    }
}