package com.gear2go_frontend.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;

@Route("login")
public class Login extends Div {

    public Login() {
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setError(true);
        add(loginOverlay);
        loginOverlay.setOpened(true);
        // Prevent the example from stealing focus when browsing the
        // documentation
        loginOverlay.getElement().setAttribute("no-autofocus", "");
    }

}