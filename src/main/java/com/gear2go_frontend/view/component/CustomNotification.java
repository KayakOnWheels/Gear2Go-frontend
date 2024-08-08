package com.gear2go_frontend.view.component;

import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class CustomNotification {


    public void showErrorNotification(String message) {
        com.vaadin.flow.component.notification.Notification notification = com.vaadin.flow.component.notification.Notification
            .show(message);
        notification.setPosition(com.vaadin.flow.component.notification.Notification.Position.MIDDLE);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
}

    public void showSuccessNotification(String message) {
        com.vaadin.flow.component.notification.Notification notification = com.vaadin.flow.component.notification.Notification
                .show(message);
        notification.setPosition(com.vaadin.flow.component.notification.Notification.Position.MIDDLE);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

}
