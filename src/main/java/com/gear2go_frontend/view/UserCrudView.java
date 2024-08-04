package com.gear2go_frontend.view;

import com.gear2go_frontend.domain.User;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.view.component.ExceptionNotification;
import com.gear2go_frontend.view.form.UserForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "user-crud", layout = Layout.class)
public class UserCrudView extends VerticalLayout {

    private UserService userService;
    private Grid<User> grid = new Grid<>(User.class);
    private TextField filter = new TextField();
    private UserForm userForm;
    private Button addNewUser = new Button("Add new User");
    private final ExceptionNotification exceptionNotification;

    @Autowired
    public UserCrudView(UserService userService, ExceptionNotification exceptionNotification) {
        this.userService = userService;
        this.exceptionNotification = exceptionNotification;
        this.userForm = new UserForm(userService, this, exceptionNotification);

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
            userForm.getSaveBtn().setVisible(true);
            userForm.getDeleteBtn().setVisible(true);
            userForm.getAddNewBtn().setVisible(false);
            userForm.setUserFormVisibility(grid.asSingleSelect().getValue());
        });

        addNewUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            userForm.getSaveBtn().setVisible(false);
            userForm.getDeleteBtn().setVisible(false);
            userForm.getAddNewBtn().setVisible(true);
            userForm.setUserFormVisibility(new User());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewUser);

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
                    exceptionNotification.showErrorNotification(exception.getMessage());
                });
    }

    private void update() {
        userService.findUsersByName(
                filter.getValue(),
                userList -> {
                    grid.setItems(userList);
                },
                exception -> {
                    exceptionNotification.showErrorNotification(exception.getMessage());
                });
    }

}