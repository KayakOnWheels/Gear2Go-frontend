package com.gear2go_frontend.view;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.stereotype.Component;

@Component
public class ExceptionNotification {


    public void showErrorNotification(String message) {
        Notification notification = Notification
            .show(message);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
}}
