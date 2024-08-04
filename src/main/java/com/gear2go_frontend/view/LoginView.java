package com.gear2go_frontend.view;

import com.gear2go_frontend.dto.AuthenticationRequest;
import com.gear2go_frontend.service.UserService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;

@Route(value = "login")
public class LoginView extends Div {

    private final UserService userService;

    public LoginView(UserService userService) {
        this.userService = userService;
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setError(false);
        add(loginOverlay);
        loginOverlay.setOpened(true);

        loginOverlay.addLoginListener(e -> {
            userService.getAuthenticationToken(new AuthenticationRequest(
                    e.getUsername(),
                    e.getPassword()
            ));
            getUI().ifPresent(ui -> ui.navigate(ProductGalleryView.class));
        });
        loginOverlay.addForgotPasswordListener(event -> getUI().ifPresent(ui -> ui.navigate(ForgotPasswordView.class)));
    }
}