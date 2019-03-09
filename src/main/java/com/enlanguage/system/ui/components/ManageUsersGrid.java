package com.enlanguage.system.ui.components;


import com.enlanguage.system.server.model.dto.UserDTO;
import com.enlanguage.system.server.model.dto.VocabularyPackageDTO;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@ViewScope
@SpringComponent
public class ManageUsersGrid extends VerticalLayout {

  private Grid<UserDTO> grid;
  private Button manageUsersButton;
  private HorizontalLayout buttons;

  public ManageUsersGrid() {
    initGrid();
  }

  public void initGrid() {
    buttons = new HorizontalLayout();
    grid = new Grid<>();
    manageUsersButton = new Button("Manage");
    grid.addColumn(UserDTO::getId).setCaption("id");
    grid.addColumn(UserDTO::getUsername).setCaption("username");
    grid.addColumn(UserDTO::getPassword).setCaption("password");
    grid.addColumn(UserDTO::getCreatedAt).setCaption("createdAt");
    grid.addColumn(UserDTO::isActive).setCaption("active");
    grid.setSizeUndefined();

    grid.setWidth("1000px");
    grid.setHeight("450px");
    buttons.addComponents(manageUsersButton);
    addComponents(buttons, grid);
  }

  public Grid<UserDTO> getGrid() {
    return grid;
  }

  public Button getManageUsersButton() {
    return manageUsersButton;
  }
}
