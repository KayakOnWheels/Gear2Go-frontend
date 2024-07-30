package com.gear2go_frontend.view;

import com.gear2go_frontend.domain.User;
import com.gear2go_frontend.domain.User;
import com.gear2go_frontend.service.UserService;
import com.gear2go_frontend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

public class UserForm extends FormLayout {

    private final TextField id = new TextField("Id");
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField mail = new TextField("Mail");
    private final TextField password = new TextField("Password");
    private final TextField accessLevel = new TextField("Access Level");
    private final UserService userService;
    private final ExceptionNotification exceptionNotification;

    @Getter
    private Button saveBtn = new Button("Save");
    @Getter
    private Button deleteBtn = new Button("Delete");
    @Getter
    private Button addNewBtn = new Button("Add");
    private Binder<User> binder = new Binder<User>(User.class);
    private UserCrudPanel userCrudPanel;

    public UserForm(UserService userService, UserCrudPanel userCrudPanel,  ExceptionNotification exceptionNotification) {
        this.userCrudPanel = userCrudPanel;
        this.userService = userService;
        this.exceptionNotification = exceptionNotification;

        HorizontalLayout buttons = new HorizontalLayout(saveBtn, deleteBtn, addNewBtn);

        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(id, firstName, lastName, mail, accessLevel, buttons);

        binder.bindInstanceFields(this);

        saveBtn.addClickListener(event -> save());
        addNewBtn.addClickListener(event -> addNew());
        deleteBtn.addClickListener(event -> delete());

        addNewBtn.setVisible(false);
        id.setReadOnly(true);
        firstName.setReadOnly(false);
        lastName.setReadOnly(false);
        mail.setReadOnly(false);
        accessLevel.setReadOnly(false);
    }

    protected void save() {
        userService.updateUser(
                binder.getBean(),
                userList -> {
                    userCrudPanel.refresh();
                    setUserFormVisibility(null);
                },
                exception -> {
                    exceptionNotification.showErrorNotification(exception.getMessage());
                });
    }

    protected void addNew() {
        try {
            userService.addUser(binder.getBean());
            userCrudPanel.refresh();
            setUserFormVisibility(null);
        } catch (Exception e) {
            exceptionNotification.showErrorNotification(e.getMessage());
        }
    }

    protected void delete() {
        userService.deleteUser(
                binder.getBean(),
                userList -> {
                    userCrudPanel.refresh();
                    setUserFormVisibility(null);
                },
                exception -> {
                    exceptionNotification.showErrorNotification(exception.getMessage());
                });
    }

    public void setUserFormVisibility(User user) {
        binder.setBean(user);

        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }
}
