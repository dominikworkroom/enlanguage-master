package com.enlanguage.system.server.model;

import com.enlanguage.system.server.model.enumeration.RoleType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "username")
  private String username;

  @NotNull
  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  @NotFound(action = NotFoundAction.IGNORE)
  private List<Role> roles;

  @Column(name = "is_active")
  private Boolean active;

  @Column(name = "created_at")
  private Date createAt = new Date();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Vocabulary> vocabularies = new ArrayList<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Note> notes;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<BoxWord> boxwords = new ArrayList<>();

  @Column(name = "numberOfRememberWords")
  private int numberOfRememberWords;

  public User(String username, String password, RoleType roleType, Boolean active, Date createAt) {
    this.username = username;
    this.password = password;
    this.active = active;
    this.createAt = createAt;
  }

  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public List<Vocabulary> getVocabularies() {
    return vocabularies;
  }

  public void setVocabularies(List<Vocabulary> vocabularies) {
    this.vocabularies = vocabularies;
  }

  public List<Note> getNotes() {
    return notes;
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }

  public int getNumberOfRememberWords() {
    return numberOfRememberWords;
  }

  public void setNumberOfRememberWords(int numberOfRememberWords) {
    this.numberOfRememberWords = numberOfRememberWords;
  }

  public List<BoxWord> getBoxwords() {
    return boxwords;
  }

  public void setBoxwords(List<BoxWord> boxwords) {
    this.boxwords = boxwords;
  }
}
