package com.enlanguage.system.server.service;

import com.enlanguage.system.server.model.dto.UserDTO;

import java.util.List;

public interface AdminPanel {

  List<UserDTO> getUsersToAdminPanel();

  void saveEditedUser(UserDTO userDTO);
}
