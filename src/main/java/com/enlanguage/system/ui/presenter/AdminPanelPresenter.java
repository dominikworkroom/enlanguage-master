package com.enlanguage.system.ui.presenter;


import com.enlanguage.system.server.model.dto.NoteDTO;
import com.enlanguage.system.server.model.dto.UserDTO;
import com.enlanguage.system.server.service.AdminPanelServiceImpl;
import com.enlanguage.system.ui.components.ManageUsersGrid;
import com.enlanguage.system.ui.view.AdminPanel;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@SpringComponent
public class AdminPanelPresenter implements Presenter, Serializable {

  private AdminPanel adminPanel;

  @Autowired
  private AdminPanelServiceImpl adminPanelService;

  @Override
  public void bind() {
    ManageUsersGrid manageUsersGrid = adminPanel.getManageUsersGrid();
    Binder<UserDTO> binderData = adminPanel.getEditUserForm().getBinderData();

    manageUsersGrid.getManageUsersButton().addClickListener((Button.ClickListener) event -> {
      final Set<UserDTO> selectedItems = manageUsersGrid.getGrid().getSelectedItems();
      if (!selectedItems.isEmpty()) {
        List<UserDTO> usersToAdminPanel = adminPanelService.getUsersToAdminPanel();

        selectedItems.forEach(userDTO -> {
          UserDTO userDTOToBind = usersToAdminPanel.stream()
              .filter(user -> user.getId() == userDTO.getId()).findFirst().get();
          UI.getCurrent().addWindow(adminPanel.getEditUserForm());
          binderData.readBean(userDTOToBind);
        });
      } else {
        Notification.show("Any user is selected.Please select user which you want to edit.",
            Type.WARNING_MESSAGE);
      }


    });

    adminPanel.getEditUserForm().getSaveEditedUser()
        .addClickListener((Button.ClickListener) event -> {
          adminPanelService.saveEditedUser(writeBeanFromBinder(binderData));
          adminPanel.getEditUserForm().close();
          transferUsersToGrid(manageUsersGrid);
        });

    adminPanel.getEditUserForm().getCancelEditedUserWindow()
        .addClickListener((Button.ClickListener) event -> {
          adminPanel.getEditUserForm().close();
        });

    transferUsersToGrid(manageUsersGrid);
  }

  private void transferUsersToGrid(ManageUsersGrid manageUsersGrid) {
    manageUsersGrid.getGrid().setItems(adminPanelService.getUsersToAdminPanel());
  }


  public void setAdminPanel(AdminPanel adminPanel) {
    this.adminPanel = adminPanel;
  }

  public UserDTO writeBeanFromBinder(Binder<UserDTO> binder) {

    UserDTO userDTO = new UserDTO();

    try {
      binder.writeBean(userDTO);
    } catch (ValidationException e) {
      Notification
          .show("Write binder error in: " + getClass().getName(), Notification.Type.ERROR_MESSAGE);

    }

    return userDTO;
  }
}
