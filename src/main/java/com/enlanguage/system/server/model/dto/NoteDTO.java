package com.enlanguage.system.server.model.dto;

import com.enlanguage.system.server.model.Note;


public class NoteDTO {

  long id;
  String theme;
  String description;
  boolean priority;

  public NoteDTO() {
  }

  public NoteDTO(Note note) {
    this.id = note.getId();
    this.theme = note.getTheme();
    this.description = note.getDescription();
    this.priority = note.isPriority();
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

  public void setPrioryty(boolean prioryty) {
    this.priority = prioryty;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
