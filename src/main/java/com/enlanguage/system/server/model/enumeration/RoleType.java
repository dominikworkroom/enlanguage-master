package com.enlanguage.system.server.model.enumeration;

public enum RoleType {

  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN");

  private final String name;

  RoleType(String name) {
    this.name = name;
  }


  public String toString() {
    return this.name;
  }

}
