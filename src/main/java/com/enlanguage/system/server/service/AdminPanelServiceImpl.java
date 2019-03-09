package com.enlanguage.system.server.service;

import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.dto.UserDTO;
import com.enlanguage.system.server.repository.UserRepository;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminPanelServiceImpl implements AdminPanel {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<UserDTO> getUsersToAdminPanel() {
    List<User> allUsers = userRepository.findAll();
    return transferToDTO(allUsers);
  }

  private List<UserDTO> transferToDTO(List<User> allUsers) {
    return allUsers.stream().map(user -> {
      UserDTO userDTO = new UserDTO();
      userDTO.setUsername(user.getUsername());
      userDTO.setPassword(user.getPassword());
      userDTO.setCreatedAt(user.getCreateAt());
      userDTO.setActive(user.getActive());
      return userDTO;
    }).collect(Collectors.toList());
  }

  public void saveEditedUser(UserDTO editedUser) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    User userToEdit = userRepository.findByUsername(editedUser.getUsername());
    userToEdit.setUsername(editedUser.getUsername());
    userToEdit.setPassword(editedUser.getPassword());
    userToEdit.setActive(editedUser.isActive());
    userRepository.save(userToEdit);

    if (editedUser.getUsername().equals(auth.getName())) {
      logout();
    }

  }

  private void logout() {

    try {
      SecurityContextHolder.clearContext();
      VaadinSession.getCurrent().close();
      Page.getCurrent().setLocation("/login");

    } catch (Exception e) {
      e.printStackTrace();

    }

  }
}
