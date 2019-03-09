package com.enlanguage.system.server.service.Auth;

import com.enlanguage.system.server.model.Role;
import com.enlanguage.system.server.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUserDetails extends User implements UserDetails {

  private static final long serialVersionUID = 1L;

  public SecurityUserDetails(User user) {
    if (user == null) {
      return;
    }
    this.setId(user.getId());
    this.setUsername(user.getUsername());
    this.setPassword(user.getPassword());
    this.setCreateAt(user.getCreateAt());
    this.setRoles(user.getRoles());
    this.setActive(user.getActive());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role : super.getRoles()) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getType().name());
      authorities.add(authority);
    }
    return authorities;
  }

  @Override
  public String getUsername() {
    return super.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

  @Override
  public String getPassword() {
    return super.getPassword();
  }


}
