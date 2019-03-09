package com.enlanguage.system.ui.form;

import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;

import java.io.File;

@SpringComponent
@ViewScope
public class LoginForm extends CustomComponent {

  private TextField user;
  private PasswordField password;
  private Button loginButton;


  public LoginForm() {
    initComponents();
  }

  private void initComponents() {

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

    // Add both to a panel

    VerticalLayout fields = new VerticalLayout(logoImage(), label, user, password, loginButton);
    fields.setSpacing(true);
    fields.setMargin(new MarginInfo(true, true, true, true));
    fields.setSizeUndefined();

    // The view root layout
    VerticalLayout viewLayout = new VerticalLayout(fields);
    viewLayout.setSizeFull();
    viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
    setCompositionRoot(viewLayout);
  }

  public Embedded logoImage() {
    ThemeResource resource = new ThemeResource("img/logo.png");
    Embedded image = new Embedded("", resource);
    return image;
  }


  public TextField getUser() {
    return user;
  }

  public void setUser(TextField user) {
    this.user = user;
  }

  public PasswordField getPassword() {
    return password;
  }

  public void setPassword(PasswordField password) {
    this.password = password;
  }

  public Button getLoginButton() {
    return loginButton;
  }

  public void setLoginButton(Button loginButton) {
    this.loginButton = loginButton;
  }
}
