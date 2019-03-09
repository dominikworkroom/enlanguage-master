package com.enlanguage.system.server.model;

import com.enlanguage.system.server.model.dto.NoteDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notes")
public class Note {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "theme")
  private String theme;

  @Column(name = "description")
  private String description;

  @Column(name = "priority")
  private boolean priority;

  @Column(name = "created_at")
  Date createdAt = new Date();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  public Note() {
  }

  public Note(NoteDTO noteDTO) {
    this.theme = noteDTO.getTheme();
    this.description = noteDTO.getDescription();
    this.priority = noteDTO.isPriority();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isPriority() {
    return priority;
  }

  public void setPriority(boolean priority) {
    this.priority = priority;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
