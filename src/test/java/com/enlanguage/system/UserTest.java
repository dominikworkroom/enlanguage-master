package com.enlanguage.system;


import com.enlanguage.system.server.model.Role;
import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.enumeration.RoleType;
import com.enlanguage.system.server.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {


  @Autowired
  UserRepository userRepository;

  @Test
  public void initCreatingUser() {

    User user = new User();
    user.setId(1L);
    user.setActive(true);
    user.setCreateAt(new Date());
    user.setPassword("user123");
    user.setUsername("user");

    initRoles(user);

    userRepository.save(user);
  }

  private void initRoles(User user) {
    Role userRole = new Role();
    userRole.setType(RoleType.ROLE_USER);
    userRole.setId(1L);
    userRole.setCreatedDate(new Date());
    userRole.setUser(user);

    Role adminRole = new Role();
    adminRole.setType(RoleType.ROLE_ADMIN);
    adminRole.setId(2L);
    adminRole.setCreatedDate(new Date());
    adminRole.setUser(user);
  }
}
