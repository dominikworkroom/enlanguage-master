package com.enlanguage.system.ui.view;


import com.enlanguage.system.EnlanguageUI;

import com.enlanguage.system.ui.components.ManageUsersGrid;
import com.enlanguage.system.ui.form.EditUserForm;
import com.enlanguage.system.ui.presenter.AdminPanelPresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;

@SpringView(name = AdminPanel.ADMIN_PANEL_VIEW_NAME, ui = EnlanguageUI.class)
@SpringComponent
@Secured({"ROLE_ADMIN"})
public class AdminPanel extends VerticalLayout implements View {

  public static final String ADMIN_PANEL_VIEW_NAME = "admin-panel";
  private ManageUsersGrid manageUsersGrid;
  private AdminPanelPresenter adminPanelPresenter;
  private EditUserForm editUserForm;
  private HorizontalLayout horizontalLayout = new HorizontalLayout();


  @PostConstruct
  private void initContent() {
    horizontalLayout.addComponents(manageUsersGrid);
    addComponents(horizontalLayout);
    initPresenter();
  }

  @Autowired
  public AdminPanel(ManageUsersGrid manageUsersGrid, AdminPanelPresenter adminPanelPresenter,
      EditUserForm editUserForm) {
    this.manageUsersGrid = manageUsersGrid;
    this.adminPanelPresenter = adminPanelPresenter;
    this.editUserForm = editUserForm;
  }

  private void initPresenter() {
    adminPanelPresenter.setAdminPanel(this);
    adminPanelPresenter.bind();
  }

  public ManageUsersGrid getManageUsersGrid() {
    return manageUsersGrid;
  }

  public EditUserForm getEditUserForm() {
    return editUserForm;
  }

  public void enter(ViewChangeListener.ViewChangeEvent event) {

  }
}
