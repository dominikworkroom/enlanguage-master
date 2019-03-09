package com.enlanguage.system;


import com.enlanguage.system.server.model.Role;
import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.enumeration.RoleType;
import com.enlanguage.system.server.repository.UserRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Theme("mytheme")
@SpringUI(path = "/login")
public class LoginUI extends UI {

  @Autowired
  VaadinSharedSecurity vaadinSecurity;

  @Autowired
  UserRepository userRepository;

  private TextField user;
  private PasswordField password;
  private Button loginButton;

  private Label loginFailedLabel;
  private Label loggedOutLabel;


  @Override
  protected void init(VaadinRequest request) {
    setSizeFull();

    Label label = new Label("<b>Welcome in enLanguage App. Please log in.</b><br><br>",
        ContentMode.HTML);

    // Create the user input field
    user = new TextField("User:");
    user.setWidth("300px");
    user.setId("username");

    // Create the password input field
    password = new PasswordField("Password:");
    password.setWidth("300px");
    password.setValue("");
    password.setId("password");

    // Create login button
    loginButton = new Button("Sign in");

    loginButton.setDisableOnClick(true);
    loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    loginButton.addClickListener(e -> login());

    // Add both to a panel
    VerticalLayout fields = new VerticalLayout(logoImage(), label, user, password, loginButton);
    fields.setSpacing(true);
    fields.setMargin(new MarginInfo(true, true, true, true));
    fields.setSizeUndefined();

    // The view root layout
    VerticalLayout viewLayout = new VerticalLayout(fields);
    viewLayout.setSizeFull();
    viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
    setContent(viewLayout);
  }

  public Embedded logoImage() {
    ThemeResource resource = new ThemeResource("img/logo.png");
    Embedded image = new Embedded("", resource);
    return image;
  }

  private void login() {
    try {
      vaadinSecurity.login(user.getValue(), password.getValue());

    } catch (AuthenticationException ex) {
      user.focus();
      user.selectAll();
      password.setValue("");
      loginFailedLabel.setValue(String.format("Login failed: %s", ex.getMessage()));
      loginFailedLabel.setVisible(true);
      if (loggedOutLabel != null) {
        loggedOutLabel.setVisible(false);
      }
    } catch (Exception ex) {
      Notification
          .show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
      LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
    } finally {
      loginButton.setEnabled(true);
    }
  }
}
