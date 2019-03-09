package com.enlanguage.system.server.service.Auth;

import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("Username not found");
    }
    SecurityUserDetails securityUserDetails = new SecurityUserDetails(user);
    Collection<? extends GrantedAuthority> authorities = securityUserDetails.getAuthorities();

    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), authorities);
  }


}
