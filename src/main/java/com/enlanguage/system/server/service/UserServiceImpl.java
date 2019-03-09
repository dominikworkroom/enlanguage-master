package com.enlanguage.system.server.service;

import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findByUsername(auth.getName());

    return user;

  }
}
