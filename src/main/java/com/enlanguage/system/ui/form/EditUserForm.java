package com.enlanguage.system.ui.form;

import com.enlanguage.system.server.model.dto.UserDTO;
import com.enlanguage.system.server.utils.validator.TextFieldValidator;
import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@ViewScope
public class EditUserForm extends Window {

  private TextField userUsername;
  private TextField userPassword;
  private Button saveEditedUser;
  private Button cancelEditedUserWindow;
  private CheckBox userActive;
  private Binder<UserDTO> binderData = new Binder<>();
  private TextFieldValidator textFieldValidator = new TextFieldValidator();

  public EditUserForm() {
    init();
    bindingData();
  }

  private void bindingData() {
    binderData.forField(userUsername).bind(UserDTO::getUsername, UserDTO::setUsername);
    binderData.forField(userPassword).bind(UserDTO::getPassword, UserDTO::setPassword);
    binderData.forField(userActive).bind(UserDTO::isActive, UserDTO::setActive);
  }

  private void init() {
    userUsername = new TextField("Username");
    userUsername.setWidth(498.0f, Unit.PIXELS);
    userPassword = new TextField("Password");
    userPassword.setWidth(498.0f, Unit.PIXELS);
    saveEditedUser = new Button("Save User");
    saveEditedUser.setStyleName(ValoTheme.BUTTON_PRIMARY);
    cancelEditedUserWindow = new Button("Cancel");
    cancelEditedUserWindow.setStyleName(ValoTheme.BUTTON_DANGER);
    userActive = new CheckBox("active");
    setWidth(557.0f, Unit.PIXELS);
    setHeight(476.0f, Unit.PIXELS);

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout(saveEditedUser,
        cancelEditedUserWindow);
    verticalLayout.addComponents(userUsername, userPassword, userActive, horizontalLayout);

    setContent(verticalLayout);

    center();
  }

  public void clearFields() {
    textFieldValidator.setStopValidation(true);
    userUsername.clear();
    textFieldValidator.setStopValidation(true);
    userPassword.clear();
    textFieldValidator.setStopValidation(true);
    userActive.clear();
  }


  public Button getSaveEditedUser() {
    return saveEditedUser;
  }

  public Button getCancelEditedUserWindow() {
    return cancelEditedUserWindow;
  }

  public Binder<UserDTO> getBinderData() {
    return binderData;
  }
}
