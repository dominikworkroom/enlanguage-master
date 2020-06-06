package com.enlanguage.system;


import com.enlanguage.system.ui.view.AdminPanel;
import com.enlanguage.system.ui.view.NoteView;
import com.enlanguage.system.ui.view.error.ErrorView;
import com.enlanguage.system.ui.view.MainView;
import com.enlanguage.system.ui.view.error.AccessDeniedView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * Created by Dominik on 19.04.2017.
 */

@Theme("mytheme")
@Title("Enlanguage App")
@SpringUI(path = "/")
@SpringViewDisplay
// comment
public class EnlanguageUI extends UI implements ViewDisplay {

  @Inject
  private SpringNavigator springNavigator;

  @Inject
  private SpringViewProvider springViewProvider;

  private Panel viewDisplayPanel;

  @PostConstruct
  public void init() {
    springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
    springNavigator.setErrorView(ErrorView.class);
    viewDisplayPanel = new Panel();
    viewDisplayPanel.setSizeFull();
  }

  @Override
  protected void init(VaadinRequest vaadinRequest) {

    final CssLayout navigationBar = new CssLayout();
    navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    navigationBar.addComponent(isLogInUsername());
    navigationBar.addComponent(createNavigationButton("Dashboard", MainView.MAIN_VIEW_NAME));
    navigationBar.addComponent(createNavigationButton("Notes", NoteView.GRAMMAR_VIEW_NAME));
    navigationBar
        .addComponent(createNavigationButton("Admin Panel", AdminPanel.ADMIN_PANEL_VIEW_NAME));
    navigationBar.addComponent(new Button("Logout", e -> logout()));

    VerticalLayout rootLayout = new VerticalLayout();
    rootLayout.addComponent(navigationBar);
    rootLayout.addComponent(viewDisplayPanel);
    rootLayout.setExpandRatio(viewDisplayPanel, 1.0f);
    rootLayout.setSizeFull();
    rootLayout.setMargin(true);
    rootLayout.setSpacing(true);

    setContent(rootLayout);
  }


  protected void logout() {

    try {
      SecurityContextHolder.clearContext();
      VaadinSession.getCurrent().close();
      Page.getCurrent().setLocation("/login");

    } catch (Exception e) {
      e.printStackTrace();

    }

  }

  protected HorizontalLayout isLogInUsername() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    HorizontalLayout userInformationPanel = new HorizontalLayout();
    userInformationPanel.setMargin(true);
    userInformationPanel.addComponent(
        new Label("You are logged in as: " + "<b>" + auth.getName() + "</b>", ContentMode.HTML));
    return userInformationPanel;
  }


  @Override
  public void showView(View view) {
    viewDisplayPanel.setContent((Component) view);
  }

  private Button createNavigationButton(String caption, final String viewName) {
    Button button = new Button(caption);
    Navigator navigator = getUI().getNavigator();

    button.addClickListener((Button.ClickListener) event -> {
      if (!navigator.getState().equals(viewName)) {
        navigator.navigateTo(viewName);
      }

    });

    return button;
  }


}
