package com.gear2go_frontend.view;

import com.gear2go_frontend.domain.User;
import com.gear2go_frontend.properties.Gear2GoServerProperties;
import com.gear2go_frontend.service.UriService;
import com.gear2go_frontend.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.function.Consumer;

@Route(value = "user-crud", layout = Layout.class)
public class UserCrudPanel extends VerticalLayout {

    private UserService userService;
    private Grid<User> grid = new Grid<>(User.class);
    private TextField filter = new TextField();
    private UserForm userForm;

    @Autowired
    public UserCrudPanel(UserService userService) {
        this.userService = userService;
        this.userForm = new UserForm(userService, this);

        filter.setPlaceholder("Filter by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());

        grid.setColumns("id", "firstName", "lastName", "mail", "accessLevel");
        HorizontalLayout mainContent = new HorizontalLayout(grid, userForm);
        mainContent.setSizeFull();
        grid.setSizeFull();
        userForm.setUserFormVisibility(null);

        grid.asSingleSelect().addValueChangeListener(e -> {
            userForm.setUserFormVisibility(grid.asSingleSelect().getValue());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filter);

        add(toolbar, mainContent);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        userService.getUserList(
                userList -> {
                    grid.setItems(userList);
                },
                exception -> {
                    Notification notification = Notification
                            .show("Something went wrong...");
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                });
    }

    private void update() {
        grid.setItems(userService.findUsersByName(filter.getValue()));
    }

}